package com.site.sbb.comment

import com.site.sbb.answer.Answer
import com.site.sbb.question.Question
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository :JpaRepository<Comment,Int> {
    fun findByQuestion(question:Question):List<Question>
    fun findByAnswer(answer:Answer):List<Question>
}