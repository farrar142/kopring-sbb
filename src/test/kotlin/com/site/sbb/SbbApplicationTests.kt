package com.site.sbb

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
class SbbApplicationTests{
	@Autowired
	lateinit var questionRepository: QuestionRepository

	@Test
	fun testJpa(){
		val q1:Question = Question()
		q1.subject = "sbb가 무엇인가요?"
		q1.content = "sbb에 대해서 알고 싶습니다."
		q1.createDate = LocalDateTime.now()
		this.questionRepository.save(q1)

		val q2:Question = Question()
		q2.subject = "sbb가 무엇인가요?"
		q2.content = "sbb에 대해서 알고 싶습니다."
		q2.createDate = LocalDateTime.now()
		this.questionRepository.save(q2)
	}

}
