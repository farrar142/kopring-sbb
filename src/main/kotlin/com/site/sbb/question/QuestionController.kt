package com.site.sbb.question

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class QuestionController {
    @GetMapping("/question/list")
    fun list():String{
        return "question_list"
    }
}