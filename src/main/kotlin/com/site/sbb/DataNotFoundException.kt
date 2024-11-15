package com.site.sbb

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value=HttpStatus.BAD_REQUEST,reason="entity not found")
class DataNotFoundException(message:String): RuntimeException(message) {
    private final val serialVersionUID:Long = 1L
}