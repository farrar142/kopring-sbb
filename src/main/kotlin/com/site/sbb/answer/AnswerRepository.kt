package com.site.sbb.answer

import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository:JpaRepository<Answer,Int> {
}