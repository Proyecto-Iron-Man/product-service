# Product Service V1

## Install dependencies

```bash
mvn clean install
```

## Local configuration

### Configuration file
To run the application locally, you can use the `application-local.yml` configuration file. This file is designed for local development and testing.

```yaml
# Database configuration
database:
  server: localhost
  port: 1433
  name: <your_database_name>
  user: <your_database_user>
  password: <your_database_password>


spring:

  # R2DBC configuration
  r2dbc:
    url: r2dbc:mssql://${database.server}:${database.port}/${database.name}
    username: ${database.user}
    password: ${database.password}
    properties:
      leakDetectionLevel: paranoid

  # Spring data jpa configuration
  datasource:
    url: jdbc:sqlserver://${database.server}:${database.port};databaseName=${database.name};encrypt=true;trustServerCertificate=true;
    username: ${database.user}
    password: ${database.password}
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
    hikari:
      pool-name: product-service-v1-pool
      connection-timeout: 30000 # Default is 30000
      validation-timeout: 3000 # Default is 5000
      maximum-pool-size: 10 # Default is 10
      minimum-idle: 5 # Default is value of maximum-pool-size
      idle-timeout: 600000 # Default is 600000
      max-lifetime: 1800000 # Default is 1800000
      leak-detection-threshold: 5000 # Default is 0
      auto-commit: true # Default is true


# Hibernate configuration
hibernate:
  dialect: org.hibernate.dialect.SQLServerDialect
  hbm2ddl:
    auto: none
  show_sql: true
  format_sql: true


# Springdoc configuration
springdoc:
  override-with-generic-response: false
```

### Environment variables
Configure the environment variable in IntelliJ IDEA or your preferred IDE to use the local profile.
```text
spring.profiles.active=local
```
