#!/usr/bin/env bash
set -euo pipefail

REPORT_FILE="${1:-test-report.md}"
README_FILE="${2:-README.md}"

awk -v report_file="$REPORT_FILE" '
  BEGIN { in_block = 0 }
  /<!-- TEST-REPORT:START -->/ {
    print
    while ((getline line < report_file) > 0) print line
    in_block = 1
    next
  }
  /<!-- TEST-REPORT:END -->/ { in_block = 0 }
  !in_block { print }
' "$README_FILE" > "${README_FILE}.tmp"

mv "${README_FILE}.tmp" "$README_FILE"
