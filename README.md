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
