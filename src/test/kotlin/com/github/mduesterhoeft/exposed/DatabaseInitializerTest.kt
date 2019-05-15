package com.github.mduesterhoeft.exposed

import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be`
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldThrow
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [com.github.mduesterhoeft.exposed.Application::class],
		properties = ["spring.exposed.generate-ddl=true",
			"spring.exposed.excluded-packages=com.github.mduesterhoeft.exposed.noscan"])
class DatabaseInitializerTest {

	@Autowired(required = false)
	var springDatabaseInitializer: SpringTransactionDatabaseInitializer? = null


	@Test @Transactional fun `should autoconfigure database`() {
		springDatabaseInitializer `should not be` null
		com.github.mduesterhoeft.exposed.Things.selectAll().count() `should be` 0
	}

	@Test
	@Transactional
	fun `should not auto migrate OtherThings table`() {
		springDatabaseInitializer `should not be` null
		invoking { com.github.mduesterhoeft.exposed.noscan.OtherThings.selectAll().count() } shouldThrow ExposedSQLException::class
	}
}

object Things: Table() {
	var id = long("id").primaryKey().autoIncrement()
	var name = varchar("name", 100)
}
