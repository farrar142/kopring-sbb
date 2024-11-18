package com.site.sbb.answer

import jakarta.validation.constraints.NotEmpty


data class AnswerForm (
    @field:NotEmpty(message="내용은 필수입니다.")
    val content:String=""){
}