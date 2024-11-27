package com.site.sbb.answer

import com.site.sbb.DataNotFoundException
import com.site.sbb.question.Question
import com.site.sbb.question.QuestionService
import com.site.sbb.user.SiteUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AnswerService(
    val answerRepository: AnswerRepository
){
    fun getAnswers(question:Question, page:Int,ordering: String):Page<Answer>{
        val sorts:ArrayList<Sort.Order> = ArrayList()
        if (ordering.equals("vote")) sorts.add(Sort.Order.desc("voter"))
        else sorts.add(Sort.Order.desc("createDate"))
        val pageable = PageRequest.of(page,10,Sort.by(sorts))
        return answerRepository.findByQuestion(question,pageable)

    }
    fun getAnswersByUser(user: SiteUser,page:Int):Page<Answer>{
        val sorts = ArrayList<Sort.Order>()
        sorts.add(Sort.Order.desc("createDate"))
        val pageable = PageRequest.of(page,10,Sort.by(sorts))
        return answerRepository.findBy
    }
    fun create(question:Question,content:String,author:SiteUser):Answer{
        val a = Answer()
        a.question=question
        a.content=content
        a.author=author
        a.createDate = LocalDateTime.now()
        this.answerRepository.save(a)
        return a
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
    fun vote(answer: Answer,siteUser: SiteUser){
        answer.voter.add(siteUser)
        answerRepository.save(answer)
    }
}