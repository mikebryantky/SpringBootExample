# SpringBoot Proof of Concept

[![Codeship Status for mikebryantky/SpringBootExample](https://app.codeship.com/projects/b80a9370-e633-0137-8614-7a7783fef01d/status?branch=master)](https://app.codeship.com/projects/373567)

## SpringBoot
 * Make a copy of the __application-development-sample.yaml__ named __application-development.yaml__
 * Edit the  __application-development.yaml__ file so that the db_name, user, and passwords match your environment.
 * Make a copy of the __application-test-sample.yaml__ named __application-test.yaml__
 * Edit the  __application-test.yaml__ file so that the db_name, user, and passwords match your environment.
 
  
 
## MySQL
**NOTE:** Be sure that that empty database schema is created prior to running. Flyway migrations will not create
MySQL databases.

 Example:
```sql
CREATE DATABASE dbname
  CHARACTER SET utf8
  COLLATE utf8_general_ci;
```

To create a backup of the MySQL database, run:
```sql
mysqldump --complete-insert --lock-all-tables --extended-insert=false --default-character-set=utf8 -uxxUSERxx -pxxPASSxx dbname | gzip -9 > dbname.sql.gz
```
  
  

## Maven
* To clean: __mvn clean__
* To build: __mvn package__ -Dspring.profiles.active=test
* To run: __mvn spring-boot:run -Dspring-boot.run.profiles=development__ (app runs at [http://localhost:8080/](http://localhost:8080/))
* To run unit tests: __mvn test -Dspring.profiles.active=test__



## Actuator
__Note:__ You must be logged before accessing any actuator endpoints.

* __Health:__ [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
* __Info:__ [http://localhost:8080/actuator/info](http://localhost:8080/actuator/info)

See [here](https://docs.spring.io/spring-boot/docs/2.0.2.BUILD-SNAPSHOT/reference/htmlsingle/#production-ready) for a full list of actuator endpoints.


## Swagger 
To access Swagger documentation, while running the app go to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Misc
* To deploy, run:
```bash
sudo java -Dspring.profiles.active=production -jar ./target/poc-0.0.1-SNAPSHOT.jar &
```

## To-Do
* Add Docker.
* Finish adding better error handling.
* Update readme to provide better overview of how code works.
* Add in-line comments to provide clear understanding of functionality.
* Add ModelMapper DTO (https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application)

