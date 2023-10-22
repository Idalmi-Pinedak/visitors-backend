package com.microservicesgt.visitors.api.controller.user.login

import com.microservicesgt.visitors.application.user.login.LoginWithEmailAndPassword
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/user"])
class LoginController {

    @Autowired
    private lateinit var loginWithEmailAndPassword: LoginWithEmailAndPassword

    @PostMapping(value = ["/login"])
    fun index(@RequestBody data: LoginJSONDTO): ResponseEntity<LoginResponseJSONDTO> {
        val email = data.email ?: ""
        val password = data.password ?: ""

        val token = loginWithEmailAndPassword.login(email, password)

        val response = LoginResponseJSONDTO.build {
            this.token = token
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

}
