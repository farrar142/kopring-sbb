package com.site.sbb.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<SiteUser,Long> {
}