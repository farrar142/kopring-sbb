package com.site.sbb.question

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@Controller
class QuestionController(
    val questionService: QuestionService
) {
    @GetMapping("/question/list")
    fun list(model:Model):String{
        val questionList = this.questionService.getList()
        model.addAttribute("questionList",questionList)
        return "question_list"
    }

    @GetMapping("/question/detail/{id}")
    fun detail(model :Model, @PathVariable("id") id : Int):String{
        val q = this.questionService.getQuestion(id)
        model.addAttribute("question",q)
        return "question_detail"
    }

}