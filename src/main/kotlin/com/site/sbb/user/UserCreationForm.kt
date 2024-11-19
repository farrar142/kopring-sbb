package com.site.sbb.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class UserCreationForm {
    @field:Size(min=3,max=25)
    @field:NotEmpty(message="사용자ID는 필수항목입니다.")
    val username:String=""

    @field:NotEmpty(message="비밀번호는 필수항목입니다.")
    val password1:String = ""

    @field:NotEmpty(message="비밀번호 확인은 필수항목입니다.")
    val password2:String=""

    @field:NotEmpty(message="이메일은 필수항목입니다.")
    @field:Email
    val email:String=""
}