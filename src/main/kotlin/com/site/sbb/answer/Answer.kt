package com.site.sbb.answer

import com.site.sbb.question.Question
import com.site.sbb.user.SiteUser
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime


@Getter
@Setter
@Entity
class Answer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id:Int = 0
    @Column(columnDefinition = "TEXT")
    var content:String = ""
    var createDate:LocalDateTime = LocalDateTime.now()
    @ManyToOne
    lateinit var question: Question

    @ManyToOne
    var author: SiteUser? = null
}