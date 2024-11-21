package com.site.sbb.question

import com.site.sbb.DataNotFoundException
import com.site.sbb.user.SiteUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional


@Service
class QuestionService(
    val questionRepository: QuestionRepository
) {
    fun getList(page:Int):Page<Question>{
        val sorts:List<Sort.Order> = ArrayList<Sort.Order>().plus(Sort.Order.desc("createDate"))
        val pageable: Pageable = PageRequest.of(page,10,Sort.by(sorts))
        return this.questionRepository.findAll(pageable)
    }

    fun getQuestion(id:Int):Question{
        val qr =  this.questionRepository.findById(id)
        if (qr.isPresent) return qr.get()
        throw DataNotFoundException("question not found")
    }
    fun create(subject:String,content:String,author:SiteUser):Question{
        val q = Question()
        q.subject=subject
        q.content=content
        q.author=author
        questionRepository.save(q)
        return q
    }
    fun modify(question: Question,subject: String,content: String){
        question.subject=subject
        question.content=content
        question.modifyDate= LocalDateTime.now()
        questionRepository.save(question)
    }
}