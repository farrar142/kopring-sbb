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
		val oq = this.questionRepository.findById(2)
		assertTrue(oq.isPresent)
		val q = oq.get()
		val a = Answer()
		a.question = q
		a.content = "네 자동으로 생성됩니다."
		a.createDate = LocalDateTime.now()
		this.answerRepository.save(a)
	}

}
