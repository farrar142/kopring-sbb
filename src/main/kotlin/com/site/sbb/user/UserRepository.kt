package com.site.sbb.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<SiteUser,Long> {
    fun findByusername(username:String):Optional<SiteUser>
}