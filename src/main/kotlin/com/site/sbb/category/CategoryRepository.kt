package com.site.sbb.category

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CategoryRepository :JpaRepository<Category,Int> {
    fun findByName(name:String):Optional<Category>
    fun findByNameIn(vararg names:String):List<Category>
}