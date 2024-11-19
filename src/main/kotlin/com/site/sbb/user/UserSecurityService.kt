package com.site.sbb.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserSecurityService:UserDetailsService {
    lateinit var userRepository: UserRepository
    override fun loadUserByUsername(username:String):User{
        val ou = userRepository.findByusername(username)
        if (ou.isEmpty){
            throw UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        }
        val su = ou.get()
        var authorities = ArrayList<GrantedAuthority>()
        if ("admin".equals(username)){
            authorities.add(SimpleGrantedAuthority(UserRole.ADMIN.name))
        }else{
            authorities.add(SimpleGrantedAuthority(UserRole.USER.name))
        }
        return  User(su.username,su.password,authorities)
    }
}