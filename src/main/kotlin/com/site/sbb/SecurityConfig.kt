package com.site.sbb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig {
    @Bean
    open fun filterChain(http:HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests{request->request.requestMatchers(AntPathRequestMatcher("/**")).permitAll()}
            .csrf{csrf->csrf.ignoringRequestMatchers(AntPathRequestMatcher("/h2-console/**"))}
            .headers{headers->headers.addHeaderWriter(XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))}
            .formLogin{form->form.loginPage("/auth/login").defaultSuccessUrl(("/"))}
            .logout { logout ->logout.logoutRequestMatcher(AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true)}
            .sessionManagement{session->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)}
        return http.build()
    }

    @Bean
    fun passwordEncoder():PasswordEncoder{
        return BCryptPasswordEncoder()
    }
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration):AuthenticationManager{
        return authenticationConfiguration.authenticationManager
    }
}