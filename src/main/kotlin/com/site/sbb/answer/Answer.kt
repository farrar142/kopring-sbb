package com.site.sbb.answer

import com.site.sbb.question.Question
import com.site.sbb.user.SiteUser
import jakarta.persistence.*
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

    var modifyDate:LocalDateTime? = null

    @ManyToMany
    var voter:MutableSet<SiteUser> = mutableSetOf()
}