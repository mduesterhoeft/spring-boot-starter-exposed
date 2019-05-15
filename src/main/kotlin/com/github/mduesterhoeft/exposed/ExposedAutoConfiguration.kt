package com.github.mduesterhoeft.exposed

import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration::class)
class ExposedAutoConfiguration(private val applicationContext: ApplicationContext) {

    @Value("\${spring.exposed.excluded-packages:}#{T(java.util.Collections).emptyList()}")
    private lateinit var excludedPackages: List<String>

    @Bean
    @ConditionalOnBean(DataSource::class)
    fun database(dataSource: DataSource): Database {
        return Database.connect(dataSource)
    }

    @Bean
    @ConditionalOnMissingBean(DatabaseInitializer::class)
    @ConditionalOnProperty("spring.exposed.generate-ddl", havingValue = "true", matchIfMissing = false)
    @ConditionalOnMissingClass("org.jetbrains.exposed.spring.SpringTransactionManager")
    fun databaseInitializer(): DatabaseInitializer {
        return SimpleTransactionDatabaseInitializer(this.applicationContext, excludedPackages)
    }
}