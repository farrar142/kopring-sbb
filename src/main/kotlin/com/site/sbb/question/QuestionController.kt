package com.site.sbb.question

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class QuestionController {
    @Autowired
    lateinit var questionService: QuestionService
    @GetMapping("/question/list")
    fun list(model:Model):String{
        val questionList = this.questionService.getList()
        model.addAttribute("questionList",questionList)
        return "question_list"
    }
}