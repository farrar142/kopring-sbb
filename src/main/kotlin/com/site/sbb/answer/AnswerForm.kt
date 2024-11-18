package com.site.sbb.answer

import jakarta.validation.constraints.NotEmpty


class AnswerForm {
    @NotEmpty(message="내용은 필수항목입니다.")
    var content:String=""
}