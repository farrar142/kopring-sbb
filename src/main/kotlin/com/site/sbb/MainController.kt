package com.site.sbb

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController {
    @GetMapping("/sbb")
    @ResponseBody
    fun index():String{
        println("hello world")
        return "hello world 2"
    }

    @GetMapping("/")
    fun root():String{
        return "redirect:/question/list"
    }
}