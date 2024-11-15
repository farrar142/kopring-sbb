package com.site.sbb.question

import org.springframework.stereotype.Service

@Service
class QuestionService (
    private var questionRepository :QuestionRepository){

    fun getList():List<Question>{
       return this.questionRepository.findAll()
    }
}