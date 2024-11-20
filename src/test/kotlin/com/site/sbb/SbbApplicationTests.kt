package com.site.sbb

import com.site.sbb.answer.AnswerRepository
import com.site.sbb.question.QuestionService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class SbbApplicationTests{
	@Autowired
	lateinit var questionService: QuestionService
	@Test
	fun testJpa(){

//		for (i in 1..300) {
//			val subject = String.format("테스트 데이터입니다:[%03d]", i)
//			val content = "내용무"
//			questionService.create(subject, content,null)
//		}
	}

}
