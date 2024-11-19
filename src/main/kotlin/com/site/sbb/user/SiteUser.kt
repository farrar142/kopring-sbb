package com.site.sbb.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class SiteUser {
    @Id
    @GeneratedValue
    var id:Long = 0L

    @Column(unique=true)
    var username:String = ""

    var password:String = ""

    @Column(unique=true)
    var email:String = ""

}