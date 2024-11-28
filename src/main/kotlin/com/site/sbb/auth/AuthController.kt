package com.site.sbb.auth

import com.site.sbb.user.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.core.env.Environment
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Controller
@RequestMapping("/auth")
class AuthController (
    private val userService: UserService,
    private val mailSender: MailSender,
    private val env: Environment,
    private val kakaoService: KakaoService,
    private val authenticationManager: AuthenticationManager
){
    @GetMapping("/signup")
    fun signup(userCreateForm: UserCreateForm):String{
        return "signup_form"
    }

    @GetMapping("/kakao/login")
    fun kakaologin():String{
        val sb = StringBuilder()
        sb.append("redirect:").append("http://kauth.kakao.com/oauth/authorize")
        sb.append("?client_id=").append(env.getProperty("KAKAO_CLIENT_KEY"))
        sb.append("&redirect_uri=").append(env.getProperty("KAKAO_REDIRECT_URI"))
        sb.append("&response_type=").append("code")
        return sb.toString()
    }

    @GetMapping("/kakao/callback")
    fun kakaocallback(@RequestParam(value="code", defaultValue = "") code: String,
                      request:HttpServletRequest):String{
        if (code.isEmpty()) throw ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 코드입니다.")
        try{

            val token = kakaoService.getAccessToken(code)
            val info = kakaoService.getKakaoInfo(token)
            val container = userService.getOrCreateKakaoUser(info)

            val authentication = UsernamePasswordAuthenticationToken(container.user.username,container.password)
            val authenticated = authenticationManager.authenticate(authentication)
            val securityContext = SecurityContextHolder.getContext()
            securityContext.authentication = authenticated
            val session = request.getSession(true)
            session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext)
        } catch (_: Exception){

        }
        return "redirect:/"
    }

    @PostMapping("/signup")
    fun signup(@Valid userCreateForm: UserCreateForm, bindingResult: BindingResult):String{
        if (bindingResult.hasErrors())return "signup_form"
        if (userCreateForm.password1!=userCreateForm.password2){
            println("password not matched")
            bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 일치하지 않습니다.")
            return "signup_form"
        }
        try{
            userService.create(userCreateForm.username,userCreateForm.email,userCreateForm.password1)
        }catch (error: DataIntegrityViolationException){
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