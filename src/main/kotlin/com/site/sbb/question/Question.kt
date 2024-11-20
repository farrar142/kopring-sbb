package com.site.sbb.question

import com.site.sbb.answer.Answer
import com.site.sbb.user.SiteUser
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

@Entity
class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(length = 200)
    var subject: String = ""

    @Column(columnDefinition = "TEXT")
    var content: String = ""
    var createDate: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "question", cascade = [CascadeType.REMOVE])
    //List는 immutable MutableList는 mutable
    var answerList: MutableList<Answer> = mutableListOf()

    @ManyToOne
    lateinit var author:SiteUser
}