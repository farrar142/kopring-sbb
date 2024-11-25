package com.site.sbb.answer

import com.site.sbb.question.Question
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository:JpaRepository<Answer,Int> {
    fun findByQuestion(question: Question, page:Pageable):Page<Answer>
}