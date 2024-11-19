package com.site.sbb.user

import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController (val userService: UserService){
    @GetMapping("/signup")
    fun signup(creationForm: UserCreationForm):String{
        return "signup_form"
    }

    @PostMapping("/signup")
    fun signup(@Valid userCreationForm: UserCreationForm,bindingResult:BindingResult):String{
        if (bindingResult.hasErrors())return "signup_form"
        if (userCreationForm.password1!=userCreationForm.password2){
            bindingResult.rejectValue("password2","passwordInCorrect")
            return "signup_form"
        }
        userService.create(userCreationForm.username,userCreationForm.email,userCreationForm.password1)
        return "redirect:/"
    }
}