package com.site.sbb

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import kotlin.test.assertEquals

@ExtendWith(SpringExtension::class)
@SpringBootTest
class SbbApplicationTests{
	@Autowired
	lateinit var questionRepository: QuestionRepository

	@Test
	fun testJpa(){
		val q = questionRepository.findBySubject("sbb가 무엇인가요?")
		assertEquals(1,q.id)
	}

}
