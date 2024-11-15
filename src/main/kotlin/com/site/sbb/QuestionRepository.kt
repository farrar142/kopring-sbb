package com.site.sbb

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface QuestionRepository:JpaRepository<Question,Int>   {
    fun findBySubject(subject:String):Question
    fun findBySubjectAndContent(subject:String,content:String):Question
}