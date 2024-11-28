package com.site.sbb.user

import com.site.sbb.answer.AnswerService
import com.site.sbb.category.CategoryService
import com.site.sbb.comment.CommentService
import com.site.sbb.question.QuestionService
import jakarta.validation.Valid
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal
import java.util.*

@Controller
@RequestMapping("/user")
class UserController (
                      val userService: UserService,
                      val categoryService: CategoryService,
                      val questionService: QuestionService,
                      val answerService: AnswerService,
                      val commentService: CommentService){
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    fun profile(model:Model,
                userPasswordChangeForm: UserPasswordChangeForm,
                bindingResult: BindingResult,principal:Principal,
                @RequestParam(value="qPage", defaultValue = "0") qPage:Int,
                @RequestParam(value="aPage", defaultValue = "0") aPage:Int,
                @RequestParam(value="cPage", defaultValue = "0") cPage:Int):String{
        val user = userService.getUser(principal.name)
        setContents(model,user,qPage=qPage,aPage=aPage,cPage=cPage)
        return "profile_detail"
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change_password")
    fun changePassword(model:Model,
                       @Valid userPasswordChangeForm: UserPasswordChangeForm,
                       bindingResult: BindingResult,principal:Principal,
                       @RequestParam(value="qPage", defaultValue = "0") qPage:Int,
                       @RequestParam(value="aPage", defaultValue = "0") aPage:Int,
                       @RequestParam(value="cPage", defaultValue = "0") cPage:Int):String{
        val user = userService.getUser(principal.name)
        setContents(model,user,qPage=qPage,aPage=aPage,cPage=cPage)
        if (bindingResult.hasErrors()) return "profile_detail"
        if (!userService.isMatchPassword(user,userPasswordChangeForm.originPassword)){
            bindingResult.rejectValue("originPassword","passwordIncorrect","기존 패스워드가 일치하지 않습니다.")
        }
        if (userPasswordChangeForm.password1 != userPasswordChangeForm.password2){
            bindingResult.rejectValue("password2","passwordNotMatched","확인 패스워드가 일치하지 않습니다.")
        }
        if (bindingResult.hasErrors()) return "profile_detail"
        userService.updatePassword(user,userPasswordChangeForm.password1)
        return "profile_detail"
    }

    private fun setContents(model: Model, user: SiteUser,
                            qPage:Int, aPage:Int, cPage:Int){
        val categoryList = categoryService.getList()
        val questionList =questionService.getListByAuthor(user,qPage)
        val answerList = answerService.getListByAuthor(user,aPage)
        val commentList = commentService.getListByAuthor(user,cPage)

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("questionPaging", questionList)
        model.addAttribute("answerPaging", answerList)
        model.addAttribute("commentPaging", commentList)
        model.addAttribute("user", user)
        model.addAttribute("qPage",qPage)
        model.addAttribute("aPage",aPage)
        model.addAttribute("cPage",cPage)
    }

    @GetMapping("/reset_password")
    fun resetPassword(model: Model):String{
        model.addAttribute("error",false)
        model.addAttribute("sendConfirm",false)
        model.addAttribute("email",false)
        return "reset_password"
    }

}