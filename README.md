# Proof-of-Concept Spring Boot 3.x multi-database support


## Description

This project is all about showing proof-of-concept for multi-database support ***WITHOUT*** using `@Primary` on any of the databases and ***WITHOUT*** naming any bean `entityManagerFactory` etc. (which would make one of the databases the primary which we have no intention on doing). 


## Databases

| Database   | vendorId     |
|------------|--------------|
| PostgreSQL | `postgresql` |
| MySQL      | `mysql`      |
| MariaDB    | `mariadb`    |
| Sqlite     | `sqlite`     |
| H2         | `h2`         |


## Requirements

- It should be possible to copy every package/classes below com.acme.poc.springboot.backend.database.postgresql to all of the com.acme.poc.springboot.backend.database.* subfolders (h2, mariadb, mysql, sqlite) ***WITHOUT*** using `@Primary` anywhere.
- When code is in place for each of the databases (this is not part of the job) it should be possible to remove any of the database subdirectories without breaking the application (besides also removing references in the PoCApplication class).
- When running the application using `mvn clean spring-boot:run` it fails with this error:
`Parameter 0 of constructor in com.acme.poc.springboot.backend.database.postgresql.service.PostgreSQLUserService required a bean named 'entityManagerFactory' that could not be found.` However, I don't want to have one of the databases to be the primary (if possible at all?). Each database should have a `<database-name>EntityManagerFactory` (fx. postgresqlEntityManagerFactory). 
- Each database configuration should be set in application.yml below `application.database.<database-name>.*` properties.
- Flyway should be able to handle SQL files in the `resources/db/*` file structure for each database.
- It is ***NOT*** part of this job to create all the other databases class files. Basically just fix the error so that the application can run together with the other requirements above. 
