package com.site.sbb.comment

import com.site.sbb.answer.Answer
import com.site.sbb.question.Question
import com.site.sbb.user.SiteUser
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import java.awt.print.Pageable

interface CommentRepository :JpaRepository<Comment,Int> {
    fun findByQuestion(question:Question):List<Comment>
    fun findByAnswer(answer:Answer):List<Comment>
    fun findByAuthor(author:SiteUser,pageable:Pageable):Page<Comment>
}