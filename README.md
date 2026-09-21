## Test Report

<!-- TEST-REPORT:START -->
❌ **Tests failing** — 53 tests, 0 failures, 1 errors, 0 skipped

| Test Class | Tests | Failures | Errors | Skipped | Time |
|---|---|---|---|---|---|
| ❌ com.example.spoon.SpoonprojectApplicationTests | 1 | 0 | 1 | 0 | 3.016s |
| ✅ com.example.spoon.controllers.AlbumControllerTest | 8 | 0 | 0 | 0 | 0.167s |
| ✅ com.example.spoon.controllers.ArtistControllerTest | 8 | 0 | 0 | 0 | 0.07s |
| ✅ com.example.spoon.controllers.SongControllerTest | 10 | 0 | 0 | 0 | 0.062s |
| ✅ com.example.spoon.services.AlbumServiceTest | 8 | 0 | 0 | 0 | 0.122s |
| ✅ com.example.spoon.services.ArtistServiceTest | 8 | 0 | 0 | 0 | 0.091s |
| ✅ com.example.spoon.services.SongServiceTest | 10 | 0 | 0 | 0 | 1.195s |

_Last updated: 2026-09-21 13:23 UTC · commit `fd6f4b6813b3dc21138af5387b9e325428bf4a47`_
<!-- TEST-REPORT:END -->

## Database Setup

This project uses **MySQL** as its database.

Each team member must have MySQL installed and running locally on their machine. You will also need to know the **username and password** for your local MySQL installation.

The project connects to MySQL using the configuration inside:

```text
src/main/resources/application.properties
```

The following properties are required:

```properties
spring.application.name=SPOONPROJECT

spring.datasource.url=jdbc:mysql://localhost:3306/spoondb?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

server.port=8091

spring.jpa.hibernate.ddl-auto=update

spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always

logging.level.org.springframework.jdbc.datasource.init=DEBUG
```

The `spring.datasource.username` and `spring.datasource.password` values must be changed to match your **own local MySQL credentials**.

The project also contains:

```text
src/main/resources/data.sql
```

This file contains the initial data for the application.

When the application is run successfully, it connects to your local MySQL server and automatically creates a database called:

```text
spoondb
```

if it does not already exist.

Hibernate then creates or updates the database tables based on the project's entity classes. Once the tables are available, Spring executes `data.sql` to populate them with the initial project data.

The database can also be connected to through IntelliJ's **Database** panel. Once connected, select/refresh the `spoondb` database to view the generated tables and their data.
