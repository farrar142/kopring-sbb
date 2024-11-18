package com.site.sbb.answer

import com.site.sbb.question.Question
import com.site.sbb.question.QuestionService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AnswerService(
    val answerRepository: AnswerRepository
){
    fun create(question:Question,content:String){
        val a = Answer()
        a.question=question
        a.content=content
        a.createDate = LocalDateTime.now()
        this.answerRepository.save(a)
    }
}