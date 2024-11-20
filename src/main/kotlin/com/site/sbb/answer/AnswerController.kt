package com.site.sbb.answer

import com.site.sbb.question.QuestionService
import com.site.sbb.user.UserService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal


@Controller
@RequestMapping("/answer")
class AnswerController(
    val questionService:QuestionService,
    val answerService: AnswerService,
    val userService:UserService
    ) {

    @PostMapping("/create/{id}")
    fun createAnswer(model:Model, @PathVariable("id") id:Int, @Valid answerForm:AnswerForm, bindingResult: BindingResult,principal: Principal):String{
        val u = userService.getUser(principal.name)
        val q = questionService.getQuestion(id)
        if (bindingResult.hasErrors()){
            model.addAttribute("question",q)
            return "question_detail"
        }
        this.answerService.create(q,answerForm.content,u)
        return String.format("redirect:/question/detail/%s",id)
    }
}