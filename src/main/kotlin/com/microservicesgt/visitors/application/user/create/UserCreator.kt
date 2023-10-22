package com.microservicesgt.visitors.application.user.create

import com.microservicesgt.visitors.application.user.UserRequest
import com.microservicesgt.visitors.application.utils.SHA256Encrypt
import com.microservicesgt.visitors.domain.entities.User
import com.microservicesgt.visitors.domain.exceptions.ValidationException
import com.microservicesgt.visitors.domain.validators.EmailValueObject
import com.microservicesgt.visitors.domain.validators.LongValueObject
import com.microservicesgt.visitors.domain.validators.StringValueObject
import com.microservicesgt.visitors.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Caso de uso crear un usuario, ya sea desde la administracion de usuarios o desde el registro de nuevos usuarios
 */
@Component
class UserCreator @Autowired constructor(private val userRepository: UserRepository) {

    /**
     * Crea un nuevo usuario
     * @param request datos para la solicitud de creacion de nuevo usuario
     * @return Long ID del nuevo usuario creado
     */
    fun create(request: UserRequest): Long {
        val sha256Encrypt = SHA256Encrypt()

        val name = StringValueObject.valueOf(request.name)
        val email = EmailValueObject.valueOf(request.email)
        val password = StringValueObject.valueOf(request.password)

        name.validate()
        email.validate()
        password.validate()

        // 1. creamos la entidad usuario
        val user = User().apply {
            this.name = name.value
            this.email = email.value
            this.password = sha256Encrypt.getSHAPassword(password.value)
            this.active = true
        }

        // 2. Verificamos que no existe un usuario con el mismo email
        val otherUser = userRepository.findByEmail(email.value)

        if (otherUser != null) {
            throw ValidationException("Ya existe un usuario con email ${email.value}")
        }

        userRepository.save(user)

        val generatedId = LongValueObject.valueOf(user.id)

        generatedId.validate()

        return generatedId.value
    }

}
