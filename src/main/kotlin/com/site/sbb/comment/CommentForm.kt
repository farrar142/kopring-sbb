package com.site.sbb.comment

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class CommentForm {
    @field:NotEmpty(message="내용은 필수입니다.")
    @field:Size(max=200)
    var content: String =""

    var answerId:Int? = null
    var questionId:Int?=null
}