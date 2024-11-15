package com.site.sbb

import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository:JpaRepository<Question,Int>   {
    fun findBySubject(subject:String):Question
}