package com.site.sbb.question

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

class QuestionForm(
     @NotEmpty(message="제목은 필수항목입니다.")@Size(max=200)  var subject: String,
    @NotEmpty(message="내용은 필수항목입니다.") var content: String
    ) {
}