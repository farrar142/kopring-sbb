package com.site.sbb.answer

import com.site.sbb.question.QuestionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/answer")
class AnswerController(val questionService:QuestionService,val answerService: AnswerService) {

    @PostMapping("/create/{id}")
    fun createAnswer(model:Model,@PathVariable("id") id:Int,@RequestParam(value="content") content:String):String{
        val q = this.questionService.getQuestion(id)
        this.answerService.create(q,content)
        return String.format("redirect:/question/detail/%s",id)
    }
}