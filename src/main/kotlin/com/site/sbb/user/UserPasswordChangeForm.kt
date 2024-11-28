package com.site.sbb.user

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class UserPasswordChangeForm (
    @field:NotEmpty(message="기존 비밀번호는 필수항목입니다.")
    var originPassword:String="",
    @field:NotEmpty(message="새 비밀번호는 필수항목입니다.")
    @field:Min(value=8,message="비밀번호는 8자리 이상이여야 됩니다")
    var password1:String="",
    @field:NotEmpty(message="새 비밀번호 확인은 필수항목입니다.")
    var password2:String=""
)