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
	@Autowired
	lateinit var answerRepository: AnswerRepository
	@Test
	fun testJpa(){
		val oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent)
		val a = oa.get()
		assertEquals(a.question.id,2)
	}

}
