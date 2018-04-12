package com.github.mduesterhoeft.exposed

import org.amshove.kluent.`should not be null`
import org.jetbrains.exposed.sql.Database
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class ExposedAutoConfigurationTest {

	@Autowired(required = false)
	var database: Database? = null


	@Test
	fun `should autoconfigure database`() {
		database.`should not be null`()
	}

}
