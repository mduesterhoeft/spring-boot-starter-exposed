[![](https://jitpack.io/v/mduesterhoeft/spring-boot-starter-exposed.svg)](https://jitpack.io/#mduesterhoeft/spring-boot-starter-exposed)
[![codecov](https://codecov.io/gh/mduesterhoeft/spring-boot-starter-exposed/branch/master/graph/badge.svg)](https://codecov.io/gh/mduesterhoeft/spring-boot-starter-exposed)

# Spring Boot Starter for Exposed

This repository adds a Spring Boot starter for the [Exposed](https://github.com/JetBrains/Exposed) - a Kotlin SQL framework.

## Autoconfiguration of Exposed beans

The starter will add a `org.jetbrains.exposed.sql.Database` bean to the application context if a bean of type `javax.sql.DataSource` is present.

If you have the exposed module `org.jetbrains.exposed:spring-transaction` on the classpath and also a `Datasource` the starter will add a `org.jetbrains.exposed.spring.SpringTransactionManager` to the application context

## Schema creation

The starter can be used to initialize the database schema.

To achieve this, a bean of type `com.md.exposed.ExposedTables` is needed on the application context. This bean needs to contain all tables that should be created. The schema generation needs to be enabled like this:

```
spring.exposed.generate-ddl=true
```
