package com.site.sbb

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime


@Getter
@Setter
@Entity
class Answer (
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id:Int,
    @Column(columnDefinition = "TEXT")
    var content:String,
    var createDate:LocalDateTime
)