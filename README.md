[![](https://jitpack.io/v/mduesterhoeft/spring-boot-starter-exposed.svg)](https://jitpack.io/#mduesterhoeft/spring-boot-starter-exposed)
[![Build Status](https://travis-ci.org/mduesterhoeft/spring-boot-starter-exposed.svg?branch=master)](https://travis-ci.org/mduesterhoeft/spring-boot-starter-exposed)
[![codecov](https://codecov.io/gh/mduesterhoeft/spring-boot-starter-exposed/branch/master/graph/badge.svg)](https://codecov.io/gh/mduesterhoeft/spring-boot-starter-exposed)

# Spring Boot Starter for Exposed

This repository adds a Spring Boot starter for the [Exposed](https://github.com/JetBrains/Exposed) - a Kotlin SQL framework.

## Getting started

The `spring-boot-starter-exposed` is available on jitpack:

```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile('com.github.mduesterhoeft:spring-boot-starter-exposed:0.2.0')
}
```

This will bring in `org.jetbrains.exposed:exposed` and `org.springframework.boot:spring-boot-starter-jdbc`.

## Autoconfiguration of Exposed beans

The starter will add a `org.jetbrains.exposed.sql.Database` bean to the application context if a bean of type `javax.sql.DataSource` is present.

If you have the exposed module `org.jetbrains.exposed:spring-transaction` on the classpath and also a `Datasource` bean on the application context, the starter will add a `org.jetbrains.exposed.spring.SpringTransactionManager` bean.

## Schema creation

The starter can be used to initialize the database schema.

The schema generation needs to be enabled via application.properties:

```
spring.exposed.generate-ddl=true
```

Packages can be excluded from schema generation by supplying a comma seperated list in application.properties:

```
spring.exposed.excluded-packages=com.package.exclude,com.package.exclude2
```

