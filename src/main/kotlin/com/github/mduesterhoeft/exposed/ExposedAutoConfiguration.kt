package com.github.mduesterhoeft.exposed

import org.jetbrains.exposed.sql.Database
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration::class)
class ExposedAutoConfiguration {

    @Bean
    @ConditionalOnBean(DataSource::class)
    fun database(dataSource: DataSource): Database {
        return Database.connect(dataSource)
    }

    @Bean
    @ConditionalOnMissingBean(DatabaseInitializer::class)
    @ConditionalOnBean(ExposedTables::class)
    @ConditionalOnProperty("spring.exposed.generate-ddl", havingValue = "true", matchIfMissing = false)
    @ConditionalOnMissingClass("org.jetbrains.exposed.spring.SpringTransactionManager")
    fun databaseInitializer(exposedTables: ExposedTables): DatabaseInitializer {
        return SimpleTransactionDatabaseInitializer(exposedTables)
    }
}
