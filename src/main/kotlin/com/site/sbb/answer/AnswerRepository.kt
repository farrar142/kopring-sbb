package com.site.sbb.answer

import com.site.sbb.question.Question
import com.site.sbb.user.SiteUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository:JpaRepository<Answer,Int> {
    fun findByQuestion(question: Question, page:Pageable):Page<Answer>
    fun findByAuthor(author:SiteUser,page: Pageable):Page<Answer>
}