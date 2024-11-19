package com.site.sbb.user

import jakarta.validation.Valid
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController (val userService: UserService){
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

    @GetMapping("/login")
    fun login():String{
        return "login_form"
    }
}