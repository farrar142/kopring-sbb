package com.site.sbb.user

import com.site.sbb.DataNotFoundException
import com.site.sbb.auth.KakaoInfo
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

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

    fun isMatchPassword(user:SiteUser, password:String):Boolean{
        return this.passwordEncoder.matches(password,user.password)
    }

    fun updatePassword(user:SiteUser,password: String):SiteUser{
        user.password = passwordEncoder.encode(password)
        userRepository.save(user)
        return user
    }

    fun getOrCreateKakaoUser(info: KakaoInfo):UserContainer{
        val email = "${info.id}@kakao.com"
        val user = userRepository.findByEmail(email)
        val password = UUID.randomUUID().toString()
        if (user.isPresent) return UserContainer(user.get(),password)
        val tempUserName = "${info.id}WithKakao"
        return UserContainer(this.create(tempUserName,email,password),password)
    }

    class UserContainer(val user: SiteUser, val password: String){}
}