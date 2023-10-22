package com.microservicesgt.visitors.api.controller.user.login

class LoginResponseJSONDTO {
    var token: String = ""

    companion object {
        fun build(init: LoginResponseJSONDTO.() -> Unit) = LoginResponseJSONDTO().apply(init)
    }
}
