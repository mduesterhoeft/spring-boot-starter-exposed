package com.github.mduesterhoeft.exposed

import com.github.mduesterhoeft.exposed.noscan.OtherThings
import org.amshove.kluent.`should be`
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [com.github.mduesterhoeft.exposed.Application::class],
        properties = ["spring.autoconfigure.exclude=com.github.mduesterhoeft.exposed.SpringTransactionExposedAutoConfiguration"])
class SimpleTransactionDatabaseInitializerTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun `should initialize database with simple initializer`() {
        Database.connect("jdbc:h2:mem:test", "org.h2.Driver")
        transaction {
            com.github.mduesterhoeft.exposed.SimpleTransactionDatabaseInitializer(applicationContext, listOf("com.github.mduesterhoeft.exposed.noscan")).run(null)
            com.github.mduesterhoeft.exposed.Things.selectAll().count() `should be` 0
            invoking { OtherThings.selectAll().count() } shouldThrow ExposedSQLException::class
        }
    }
}
