package com.site.sbb.answer

import com.site.sbb.question.QuestionService
import com.site.sbb.user.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import java.security.Principal


@Controller
@RequestMapping("/answer")
class AnswerController(
    val questionService:QuestionService,
    val answerService: AnswerService,
    val userService:UserService
    ) {

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    fun answerModify(@PathVariable("id") id:Int,answerForm: AnswerForm,principal: Principal):String {
        val answer = answerService.getAnswer(id)
        if (!answer.author?.username.equals(principal.name))throw ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.")
        answerForm.content= answer.content
        return "answer_form"
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    fun answerModify(@PathVariable("id") id:Int,
                     @Valid answerForm: AnswerForm,
                     bindingResult: BindingResult,
                     principal: Principal):String{
        if (bindingResult.hasErrors())throw ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.")
        val answer = answerService.getAnswer(id)
        answerService.modify(answer,answerForm.content)
        return String.format("redirect:/question/detail/%s",answer.question.id)
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    fun answerDelete(@PathVariable("id") id:Int,principal: Principal):String{
        val answer = answerService.getAnswer(id)
        if (!answer.author?.username.equals(principal.name)) throw ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.")
        answerService.delete(answer)
        return String.format("redirect:/question/detail/%s",answer.question.id)
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    fun answerVote(@PathVariable("id") id:Int, principal: Principal):String{
        val answer = answerService.getAnswer(id)
        val siteUser = userService.getUser(principal.name)
        answerService.vote(answer,siteUser)
        return String.format("redirect:/question/detail/%s",answer.question.id)
    }
}