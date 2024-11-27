package com.site.sbb.question

import com.site.sbb.answer.AnswerForm
import com.site.sbb.answer.AnswerService
import com.site.sbb.category.CategoryService
import com.site.sbb.comment.CommentForm
import com.site.sbb.user.SiteUser
import com.site.sbb.user.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
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
@RequestMapping("/question")
class QuestionController(
    val questionService: QuestionService,
    val userService: UserService,
    val answerService: AnswerService,
    val categoryService: CategoryService
) {
    @GetMapping("/list")
    fun list(model:Model,
             @RequestParam(value="page",defaultValue="0") page:Int,
             @RequestParam(value="kw", defaultValue = "") kw:String,
    ):String{
        val categoryList = categoryService.getList()
        val paging = questionService.getList(page,kw)
        model.addAttribute("paging",paging)
        model.addAttribute("categoryList",categoryList)
        return "question_list"
    }

    @GetMapping("/detail/{id}")
    fun detail(model :Model,
               @PathVariable("id") id : Int,
               @RequestParam(value="answerPage", defaultValue = "0") answerPage:Int,
               @RequestParam(value="answerOrdering", defaultValue = "vote") answerOrdering:String,
               answerForm:AnswerForm,
               commentForm: CommentForm,
               ):String{
        val q = this.questionService.getQuestion(id)
        val answerPaging = this.answerService.getAnswers(q,answerPage,answerOrdering)
        val categoryList = categoryService.getList()
        model.addAttribute("question",q)
        model.addAttribute("answerPaging",answerPaging)
        model.addAttribute("answerOrdering",answerOrdering)
        model.addAttribute("categoryList",categoryList)
        return "question_detail"
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    fun questionCreate(model: Model,questionForm: QuestionForm):String {
        val categoryList = categoryService.getList()
        model.addAttribute("categoryList",categoryList)
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    fun questionCreate(@Valid questionForm:QuestionForm,br:BindingResult,principal:Principal):String{
        if (br.hasErrors())return "question_form";
        val u = userService.getUser(principal.name)
        val q = questionService.create(subject=questionForm.subject,content=questionForm.content,author=u)
        return "redirect:/question/list"
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    fun questionModify(@PathVariable("id") id:Int,
                       model: Model,questionForm: QuestionForm,
                       principal: Principal):String{
        val question = questionService.getQuestion(id)
        if (!question.author?.username.equals(principal.name))throw ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.")
        questionForm.subject = question.subject
        questionForm.content = question.content
        val categoryList = categoryService.getList()
        model.addAttribute("categoryList",categoryList)
        return "question_form"
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    fun questionModify(@PathVariable("id") id:Int,
                       @Valid questionForm: QuestionForm,
                       bindingResult: BindingResult,
                       principal: Principal):String{
        if (bindingResult.hasErrors()){return "question_form"}
        val question = questionService.getQuestion(id)
        if (!question.author?.username.equals(principal.name))throw ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.")
        questionService.modify(question,questionForm.subject,questionForm.content)
        return String.format("redirect:/question/detail/%s",id)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    fun questionDelete(@PathVariable("id") id:Int,principal: Principal):String{
        val question = questionService.getQuestion(id)
        if (!question.author?.username.equals(principal.name)){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.")
        }
        questionService.delete(question)
        return "redirect:/"
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    fun questionVote(@PathVariable("id") id:Int, principal: Principal):String{
        val question:Question = questionService.getQuestion(id)
        val siteUser:SiteUser= userService.getUser(principal.name)
        questionService.vote(question,siteUser)
        return String.format("redirect:/question/detail/%s",id)
    }
}