package com.site.sbb

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@SpringBootTest
class SbbApplicationTests{
	@Autowired
	lateinit var questionRepository: QuestionRepository

	@Test
	fun testJpa(){
		assertEquals(2,this.questionRepository.count())
		val oq = this.questionRepository.findById(1)
		assertTrue(oq.isPresent)
		val q = oq.get()
		this.questionRepository.delete(q)
		assertEquals(1,this.questionRepository.count())
	}

}
