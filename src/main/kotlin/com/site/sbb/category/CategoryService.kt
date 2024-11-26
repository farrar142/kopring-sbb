package com.site.sbb.category

import com.site.sbb.DataNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CategoryService (
    val categoryRepository: CategoryRepository
){
    fun createCategory(name:String):Category{
        val category = Category().also {it.name=name}
        categoryRepository.save(category)
        return category
    }

    fun getCategoryList():List<Category>{
        return categoryRepository.findAll()
    }

    fun getCategoryByName(name:String):Category{
        val category = categoryRepository.findByName(name)
        if (category.isPresent)return category.get()
        throw DataNotFoundException("category not found")
    }

    fun categoryModify(category: Category,name: String):Category{
        category.also { it.name=name }
        categoryRepository.save(category)
        return category
    }
}