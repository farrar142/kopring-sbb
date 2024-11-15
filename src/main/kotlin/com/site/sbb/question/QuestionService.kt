package com.site.sbb.question

import org.springframework.stereotype.Service


@Service
class QuestionService(
    val questionRepository: QuestionRepository
) {
    fun getList():List<Question>{
        return this.questionRepository.findAll()
    }
}