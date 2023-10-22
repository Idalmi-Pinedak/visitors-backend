package com.microservicesgt.visitors.application.user.login

import com.microservicesgt.visitors.application.utils.JwtUtil
import com.microservicesgt.visitors.application.utils.SHA256Encrypt
import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.validators.EmailValueObject
import com.microservicesgt.visitors.domain.validators.StringValueObject
import com.microservicesgt.visitors.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LoginWithEmailAndPassword @Autowired constructor(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil
) {

    private val shA256Encrypt = SHA256Encrypt()

    /**
     * Realiza login con email y password, si el usuario existe y el password es correcto retorna un Json Web Token
     * @param email Email del usuario
     * @param password Password del usuario
     * @return Json Web Token
     */
    fun login(email: String, password: String): String {
        // 1. Verificamos los datos requeridos
        val userEmail = EmailValueObject.valueOf(email)
        val userPassword = StringValueObject.valueOf(password)

        userEmail.validate()
        userPassword.validate()

        // 2. Buscamos el usuario por su email
        val user = userRepository.findByEmail(userEmail.value)
            ?: throw ValidationException("Usuario o password incorrectos")

        // 3. Codificamos el password en SHA256 para compararlo con el password del usuario
        val encodedPassword = shA256Encrypt.getSHAPassword(password)

        // 4. Hacemos la comparacion de passwords
        // Si el password es correcto, realizamos login, creamos token y retornamos la respuesta
        // de lo contrario retornamos un error
        if (encodedPassword == user.password) {
            return jwtUtil.generateToken(user)
        } else {
            throw ValidationException("Usuario o password incorrectos")
        }
    }

}
