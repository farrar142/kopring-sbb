package com.site.sbb.comment

import com.site.sbb.answer.Answer
import com.site.sbb.answer.AnswerService
import com.site.sbb.question.Question
import com.site.sbb.question.QuestionService
import com.site.sbb.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException
import java.security.Principal
import java.util.Optional


@RequestMapping("/comment")
@Controller
class CommentController(
    val commentService: CommentService,
    val questionService: QuestionService,
    val answerService: AnswerService,
    val userService: UserService
) {
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    fun createComment(commentForm: CommentForm,
                      bindingResult: BindingResult,
                      principal: Principal):String{
        val siteUser = userService.getUser(principal.name)
        val question: Question? = commentForm.questionId?.let{questionService.getQuestion(it)}
        val answer: Answer?= commentForm.answerId?.let{answerService.getAnswer(it)}
        if (question==null && answer==null){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
        commentService.createComment(question,answer,siteUser,commentForm.content)

        val qId = question?.id ?: answer?.question?.id ?:0
        return String.format("redirect:/question/detail/%s",qId)
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("delete/{id}")
    fun commentDelete(
        @PathVariable("id") id:Int,
        principal: Principal
    ):String {
        val comment = commentService.getComment(id)
        if (!comment.author?.username.equals(principal.name)){
            throw ResponseStatusException(HttpStatus.BAD_GATEWAY,"삭제권한이 없습니다.")
        }
        val qId = comment.question?.id?:comment.answer?.question?.id?: throw ResponseStatusException(HttpStatus.BAD_GATEWAY,"옳바르지 않은 요청입니다.")
        commentService.deleteComment(comment)
        return String.format("redirect:/question/detail/%s",qId)
    }
}