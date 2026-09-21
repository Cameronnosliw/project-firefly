#!/usr/bin/env bash
set -euo pipefail

REPORT_DIR="target/surefire-reports"
OUT_FILE="${1:-test-report.md}"

total_tests=0
total_failures=0
total_errors=0
total_skipped=0

rows=""

extract_attr() {
  # $1 = file, $2 = attribute name
  grep -o "$2=\"[^\"]*\"" "$1" | head -1 | sed "s/$2=\"//;s/\"//"
}

if compgen -G "$REPORT_DIR/TEST-*.xml" > /dev/null; then
  for f in "$REPORT_DIR"/TEST-*.xml; do
    name=$(extract_attr "$f" name)
    tests=$(extract_attr "$f" tests)
    failures=$(extract_attr "$f" failures)
    errors=$(extract_attr "$f" errors)
    skipped=$(extract_attr "$f" skipped)
    time=$(extract_attr "$f" time)

    tests=${tests:-0}; failures=${failures:-0}; errors=${errors:-0}; skipped=${skipped:-0}

    if [ "$failures" -eq 0 ] && [ "$errors" -eq 0 ]; then
      icon="✅"
    else
      icon="❌"
    fi

    rows="${rows}| ${icon} ${name} | ${tests} | ${failures} | ${errors} | ${skipped} | ${time}s |
"

    total_tests=$((total_tests + tests))
    total_failures=$((total_failures + failures))
    total_errors=$((total_errors + errors))
    total_skipped=$((total_skipped + skipped))
  done
else
  rows="| _no test result files found_ | | | | | |
"
fi

if [ "$total_failures" -eq 0 ] && [ "$total_errors" -eq 0 ]; then
  overall="✅ **All tests passing**"
else
  overall="❌ **Tests failing**"
fi

{
  echo "$overall — ${total_tests} tests, ${total_failures} failures, ${total_errors} errors, ${total_skipped} skipped"
  echo ""
  echo "| Test Class | Tests | Failures | Errors | Skipped | Time |"
  echo "|---|---|---|---|---|---|"
  printf '%s' "$rows"
  echo ""
  echo "_Last updated: $(date -u '+%Y-%m-%d %H:%M UTC') · commit \`${GITHUB_SHA:-local}\`_"
} > "$OUT_FILE"
