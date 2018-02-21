package com.md.exposed

import org.amshove.kluent.`should be`
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Test


class SimpleTransactionDatabaseInitializerTest {

    @Test
    fun `should initialize database with simple initializer`() {
        Database.connect("jdbc:h2:mem:test", "org.h2.Driver")
        transaction {
            SimpleTransactionDatabaseInitializer(ExposedTables(listOf(Things))).run(null)
            Things.selectAll().count() `should be` 0
        }
    }
}