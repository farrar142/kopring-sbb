package com.site.sbb.comment

import com.site.sbb.answer.Answer
import com.site.sbb.question.Question
import com.site.sbb.user.SiteUser
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id:Int = 0

    @Column(columnDefinition = "TEXT")
    var content: String = ""

    var createDate: LocalDateTime = LocalDateTime.now()

    var modifyDate:LocalDateTime?= null

    @ManyToOne
    var question: Question? = null

    @ManyToOne
    var answer: Answer? = null

    @ManyToOne
    var author:SiteUser? = null
}