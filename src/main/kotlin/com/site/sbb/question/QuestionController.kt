package com.site.sbb.question

import com.site.sbb.answer.AnswerForm
import com.site.sbb.user.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal


@Controller
@RequestMapping("/question")
class QuestionController(
    val questionService: QuestionService,
    val userService: UserService
) {
    @GetMapping("/list")
    fun list(model:Model,@RequestParam(value="page",defaultValue="0") page:Int):String{
        val paging = this.questionService.getList(page)
        model.addAttribute("paging",paging)
        return "question_list"
    }

    @GetMapping("/detail/{id}")
    fun detail(model :Model, @PathVariable("id") id : Int,answerForm:AnswerForm):String{
        val q = this.questionService.getQuestion(id)
        model.addAttribute("question",q)
        return "question_detail"
    }

    @GetMapping("/create")
    fun questionCreate(questionForm: QuestionForm):String{
        return "question_form";
    }

    @PostMapping("/create")
    fun questionCreate(@Valid questionForm:QuestionForm,br:BindingResult,principal:Principal):String{
        if (br.hasErrors())return "question_form";
        val u = userService.getUser(principal.name)
        val q = questionService.create(subject=questionForm.subject,content=questionForm.content,author=u)
        return "redirect:/question/list"
    }

}