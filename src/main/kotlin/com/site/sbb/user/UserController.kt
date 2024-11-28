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
                      val mailSender:MailSender,
                      val categoryService: CategoryService,
                      val questionService: QuestionService,
                      val answerService: AnswerService,
                      val commentService: CommentService){
    @GetMapping("/signup")
    fun signup(userCreateForm: UserCreateForm):String{
        return "signup_form"
    }

    @PostMapping("/signup")
    fun signup(@Valid userCreateForm: UserCreateForm, bindingResult:BindingResult):String{
        if (bindingResult.hasErrors())return "signup_form"
        if (userCreateForm.password1!=userCreateForm.password2){
            println("password not matched")
            bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 일치하지 않습니다.")
            return "signup_form"
        }
        try{
            userService.create(userCreateForm.username,userCreateForm.email,userCreateForm.password1)
        }catch (error:DataIntegrityViolationException){
            error.printStackTrace()
            bindingResult.reject("signupFailed","이미 등록된 사용자입니다.")
            return "signup_form"
        }catch (error:Exception){
            error.printStackTrace()
            bindingResult.reject("signupFailed",error.message.toString())
            return "signup_form"
        }
        return "redirect:/"
    }

    private fun setContents(model: Model,user: SiteUser,
                            qPage:Int,aPage:Int,cPage:Int){
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

    @GetMapping("/reset_password")
    fun resetPassword(model: Model):String{
        model.addAttribute("error",false)
        model.addAttribute("sendConfirm",false)
        model.addAttribute("email",false)
        return "reset_password"
    }

    @PostMapping("/reset_password")
    fun resetPassword(model: Model,
                      @RequestParam(value="email") email:String):String{
        var error = false
        var sendConfirm = true
        try{
            val user = userService.getUserByEmail(email)
            val simpleMailMessage = SimpleMailMessage()
            simpleMailMessage.setTo(email)
            simpleMailMessage.subject="계정 정보입니다."
            val newPassword = UUID.randomUUID().toString().replace("-","")
            val sb = StringBuilder().run{
                append(user.username)
                append("계정의 비밀번호를 새롭게 초기화 했습니다.\n")
                append("새 비밀번호는 ").append(newPassword).append(" 입니다.\n")
                append("로그인 후 내 정보에서 새로 비밀번호를 지정해주세요")
            }
            simpleMailMessage.text = sb.toString()
            userService.updatePassword(user,newPassword)
            Thread{mailSender.send(simpleMailMessage)}.start()
        }catch (e:Exception) {
            error = true
            sendConfirm = false
        }
        model.addAttribute("error", error)
        model.addAttribute("sendConfirm", sendConfirm)
        model.addAttribute("email", email)
        return "reset_password"
    }

    @GetMapping("/login")
    fun login():String{
        return "login_form"
    }
}