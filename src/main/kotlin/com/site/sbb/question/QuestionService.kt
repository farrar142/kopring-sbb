package com.site.sbb.question

import com.site.sbb.DataNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class QuestionService(
    val questionRepository: QuestionRepository
) {
    fun getList(page:Int):Page<Question>{
        val pageable: Pageable = PageRequest.of(page,10)
        return this.questionRepository.findAll(pageable)
    }

    fun getQuestion(id:Int):Question{
        val qr =  this.questionRepository.findById(id)
        if (qr.isPresent) return qr.get()
        throw DataNotFoundException("question not found")
    }
    fun create(subject:String,content:String):Question{
        val q = Question()
        q.subject=subject
        q.content=content
        questionRepository.save(q)
        return q
    }
}