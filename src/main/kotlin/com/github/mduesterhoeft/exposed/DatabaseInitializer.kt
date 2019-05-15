package com.github.mduesterhoeft.exposed

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.AutoConfigurationPackages
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.Ordered
import org.springframework.core.type.filter.AssignableTypeFilter
import org.springframework.core.type.filter.RegexPatternTypeFilter
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern

@Deprecated("Annotation is no longer used", level = DeprecationLevel.ERROR)
data class ExposedTables(val tables: List<Table>)

interface DatabaseInitializer : ApplicationRunner, Ordered {
    override fun getOrder(): Int = DATABASE_INITIALIZER_ORDER

    companion object {
        const val DATABASE_INITIALIZER_ORDER = 0
    }
}

open class SimpleTransactionDatabaseInitializer(private val applicationContext: ApplicationContext, private val excludedPackages: List<String>) : DatabaseInitializer {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        val exposedTables = discoverExposedTables(applicationContext, excludedPackages)
        log.info("creating Schema for tables '{}'", exposedTables.map { it.tableName })
        transaction {
            log.info("ddl {}", exposedTables.map { it.ddl }.joinToString())
            SchemaUtils.create(*exposedTables.toTypedArray())
        }
    }
}

@Transactional
open class SpringTransactionDatabaseInitializer(private val applicationContext: ApplicationContext, private val excludedPackages: List<String>) : DatabaseInitializer {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        val exposedTables = discoverExposedTables(applicationContext, excludedPackages)
        log.info("creating Schema for tables '{}'", exposedTables.map { it.tableName })

        log.info("ddl {}", exposedTables.map { it.ddl }.joinToString())
        SchemaUtils.create(*exposedTables.toTypedArray())
    }
}

/**
 * Discovers any class that extends org.jetbrains.exposed.sql.Table unless package is excluded
 */
fun discoverExposedTables(applicationContext: ApplicationContext, excludedPackages: List<String>): List<Table> {
    val provider = ClassPathScanningCandidateComponentProvider(false)
    provider.addIncludeFilter(AssignableTypeFilter(Table::class.java))
    excludedPackages
            .forEach { pkg ->
                provider
                        .addExcludeFilter(RegexPatternTypeFilter(Pattern
                                .compile(pkg.replace(".", "\\.") + ".*")))
            }

    val packages = AutoConfigurationPackages.get(applicationContext)

    val components = packages.map { pkg -> provider.findCandidateComponents(pkg) }.flatten()

    return components.map { component -> Class.forName(component.beanClassName).kotlin.objectInstance as Table }
}

