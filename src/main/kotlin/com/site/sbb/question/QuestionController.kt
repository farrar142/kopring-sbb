package com.site.sbb.question

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class QuestionController {
    @Autowired
    lateinit var questionRepository: QuestionRepository
    @GetMapping("/question/list")
    fun list(model:Model):String{
        val questionList = this.questionRepository.findAll()
        model.addAttribute("questionList",questionList)
        return "question_list"
    }
}