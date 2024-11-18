package com.site.sbb.question

import com.site.sbb.DataNotFoundException
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class QuestionService(
    val questionRepository: QuestionRepository
) {
    fun getList():List<Question>{
        return this.questionRepository.findAll()
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