package com.site.sbb.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun create(username:String,email:String,password:String):SiteUser{
        val u = SiteUser()
        u.username = username
        u.email = email
        val passwordEncoder =  BCryptPasswordEncoder()
        val encodedPassword = passwordEncoder.encode(password)
        u.password = encodedPassword
        userRepository.save(u)
        return u
    }
}