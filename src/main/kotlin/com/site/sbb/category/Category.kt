package com.site.sbb.category

import com.site.sbb.question.Question
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int = 0

    @Column(unique=true)
    var name:String =""

    @OneToMany(mappedBy = "category",cascade=[CascadeType.REMOVE])
    var questionList:List<Question> = mutableListOf()
}