package com.md.exposed

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
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
}