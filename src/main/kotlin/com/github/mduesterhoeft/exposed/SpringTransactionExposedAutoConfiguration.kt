package com.github.mduesterhoeft.exposed

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
@ConditionalOnClass(SpringTransactionManager::class)
class SpringTransactionExposedAutoConfiguration {

    @Bean
    fun springExposedTransactionManager(dataSource: DataSource): SpringTransactionManager {
        return SpringTransactionManager(dataSource)
    }

    @Bean
    @ConditionalOnMissingBean(com.github.mduesterhoeft.exposed.DatabaseInitializer::class)
    @ConditionalOnBean(com.github.mduesterhoeft.exposed.ExposedTables::class)
    @ConditionalOnProperty("spring.exposed.generate-ddl", havingValue = "true", matchIfMissing = false)
    fun databaseInitializer(exposedTables: com.github.mduesterhoeft.exposed.ExposedTables): com.github.mduesterhoeft.exposed.DatabaseInitializer {
        return com.github.mduesterhoeft.exposed.SpringTransactionDatabaseInitializer(exposedTables)
    }
}
