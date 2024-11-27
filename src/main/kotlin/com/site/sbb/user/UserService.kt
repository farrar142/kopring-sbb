package com.site.sbb.user

import com.site.sbb.DataNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(val userRepository: UserRepository,val passwordEncoder: PasswordEncoder) {
    fun getUser(username:String):SiteUser{
        val siteUser:Optional<SiteUser> = userRepository.findByusername(username)
        if (siteUser.isPresent)return siteUser.get()
        throw DataNotFoundException("siteuser not found")
    }
    fun getUserByEmail(email:String):SiteUser{
        val siteUser = userRepository.findByEmail(email)
        if (siteUser.isPresent)return siteUser.get()
        throw DataNotFoundException("siteuser not found")
    }
    fun create(username:String,email:String,password:String):SiteUser{
        val u = SiteUser()
        u.username = username
        u.email = email
        println(password)
        val encodedPassword = passwordEncoder.encode(password)
        u.password = encodedPassword
        userRepository.save(u)
        return u
    }
    fun updatePassword(user:SiteUser,password: String):SiteUser{
        user.password = passwordEncoder.encode(password)
        userRepository.save(user)
        return user
    }
}