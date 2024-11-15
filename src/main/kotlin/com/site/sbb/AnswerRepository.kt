package com.site.sbb

import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository:JpaRepository<Answer,Int> {
}