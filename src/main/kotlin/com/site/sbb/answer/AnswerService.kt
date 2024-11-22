package com.site.sbb.answer

import com.site.sbb.DataNotFoundException
import com.site.sbb.question.Question
import com.site.sbb.question.QuestionService
import com.site.sbb.user.SiteUser
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AnswerService(
    val answerRepository: AnswerRepository
){
    fun create(question:Question,content:String,author:SiteUser){
        val a = Answer()
        a.question=question
        a.content=content
        a.author=author
        a.createDate = LocalDateTime.now()
        this.answerRepository.save(a)
    }
    fun getAnswer(id:Int):Answer{
        val answer = answerRepository.findById(id)
        if (answer.isPresent)return answer.get()
        throw DataNotFoundException("answer not found")
    }
    fun modify(answer: Answer,content: String){
        answer.content=content
        answer.modifyDate= LocalDateTime.now()
        answerRepository.save(answer)
    }
    fun delete(answer:Answer){
        answerRepository.delete(answer)
    }
}