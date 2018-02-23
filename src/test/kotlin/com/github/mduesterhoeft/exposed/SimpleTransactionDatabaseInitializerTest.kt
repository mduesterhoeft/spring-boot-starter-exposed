package com.github.mduesterhoeft.exposed

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
            com.github.mduesterhoeft.exposed.SimpleTransactionDatabaseInitializer(com.github.mduesterhoeft.exposed.ExposedTables(listOf(com.github.mduesterhoeft.exposed.Things))).run(null)
            com.github.mduesterhoeft.exposed.Things.selectAll().count() `should be` 0
        }
    }
}
