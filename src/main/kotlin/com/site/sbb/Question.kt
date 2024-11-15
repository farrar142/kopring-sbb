package com.site.sbb

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import lombok.Getter
import lombok.Setter
import lombok.Builder
import java.time.LocalDateTime

@Getter
@Setter
@Entity
class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id :Int,

    @Column(length=200)
    var subject:String,

    @Column(columnDefinition = "TEXT")
    var content : String,
    var createDate: LocalDateTime
)