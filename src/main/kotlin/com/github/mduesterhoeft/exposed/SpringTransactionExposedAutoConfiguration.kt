package com.github.mduesterhoeft.exposed

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
@ConditionalOnClass(SpringTransactionManager::class)
class SpringTransactionExposedAutoConfiguration(private val applicationContext: ApplicationContext) {

    @Value("\${spring.exposed.excluded-packages:}#{T(java.util.Collections).emptyList()}")
    private lateinit var excludedPackages: List<String>

    @Bean
    fun springExposedTransactionManager(dataSource: DataSource): SpringTransactionManager {
        return SpringTransactionManager(dataSource)
    }

    @Bean
    @ConditionalOnMissingBean(DatabaseInitializer::class)
    @ConditionalOnProperty("spring.exposed.generate-ddl", havingValue = "true", matchIfMissing = false)
    fun databaseInitializer(): DatabaseInitializer {
        return SpringTransactionDatabaseInitializer(this.applicationContext, this.excludedPackages)
    }
}
