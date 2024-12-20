package com.site.sbb.auth

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class KakaoService(
    private val env:Environment
) {
    fun getAccessToken(code:String):String {
        // Header 생성
        val headers = HttpHeaders()
        headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8")

        // Body 생성
        val body = LinkedMultiValueMap<String,String>()
        body.add("grant_type", "authorization_code")
        body.add("client_id", env.getProperty("KAKAO_CLIENT_KEY"))
        body.add("redirect_uri", env.getProperty("KAKAO_REDIRECT_URI"))
        body.add("code", code)
        body.add("client_secret", env.getProperty("KAKAO_SECRET_KEY"))

        // 요청 보내기
        val kakaoTokenRequest = HttpEntity<MultiValueMap<String, String>>(body,headers)
        val rt = RestTemplate()
        val response = rt.exchange("https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,kakaoTokenRequest,
            String::class.java)

        // 응답 -> 토큰 파싱
        val responseBody = response.body
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(responseBody)
        return jsonNode.get("access_token").asText()
    }

    @Throws(JsonProcessingException::class)
    fun getKakaoInfo(accessToken: String): KakaoInfo {
        // Header 생성
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        // 요청 보내기
        val kakaoUserInfoRequest = HttpEntity<MultiValueMap<String, String>>(headers)
        val rt = RestTemplate()
        val response = rt.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoUserInfoRequest,
            String::class.java
        )
        // body 있는 정보 꺼내기
        val responseBody = response.body
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(responseBody)

        val id = jsonNode["id"].asLong()
        val nickname = jsonNode["properties"]["nickname"].asText()

        return KakaoInfo(id, nickname)
    }
}