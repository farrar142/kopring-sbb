package com.site.sbb.question

import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository:JpaRepository<Question,Int>   {
    fun findBySubject(subject:String): Question
    fun findBySubjectAndContent(subject:String,content:String): Question
    fun findBySubjectLike(subject:String):List<Question>
}