package com.md.exposed

import org.amshove.kluent.`should not be`
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class SpringTransactionAutoConfigurationTest {

	@Autowired(required = false)
	var springTransactionManager: SpringTransactionManager? = null

	@Test
	fun `should autoconfigure spring transaction manager`() {
		springTransactionManager `should not be` null
	}

}
