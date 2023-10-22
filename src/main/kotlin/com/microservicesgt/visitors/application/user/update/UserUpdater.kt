package com.microservicesgt.visitors.application.user.update

import com.microservicesgt.visitors.application.user.UserRequest
import com.microservicesgt.visitors.application.utils.SHA256Encrypt
import com.microservicesgt.visitors.domain.exceptions.EntityNotFoundException
import com.microservicesgt.visitors.domain.validators.EmailValueObject
import com.microservicesgt.visitors.domain.validators.LongValueObject
import com.microservicesgt.visitors.domain.validators.StringValueObject
import com.microservicesgt.visitors.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserUpdater @Autowired constructor(private val userRepository: UserRepository) {

    private companion object {
        const val DEFAULT_PASSWORD = "12345678"
    }

    private val shA256Encrypt = SHA256Encrypt()

    /**
     * Actualiza los datos de un usuario
     * @param request datos para la solicitud de actualizacion de usuario
     */
    fun update(request: UserRequest) {
        val id = LongValueObject.valueOf(request.userId)
        id.validate()

        val user = userRepository.findById(id.value)
            .orElseThrow { EntityNotFoundException("Entidad usuario con ID $id no existe") }

        val name = StringValueObject.valueOf(request.name)
        val email = EmailValueObject.valueOf(request.email)

        name.validate()
        email.validate()

        user.apply {
            this.email = email.value
            this.name = name.value
            this.active = request.active
        }

        userRepository.save(user)
    }

    fun resetPassword(userId: Long) {
        val id = LongValueObject.valueOf(userId)
        id.validate()

        val user = userRepository.findById(id.value)
            .orElseThrow { EntityNotFoundException("Entidad usuario con ID $id no existe") }

        val encodedPassword = shA256Encrypt.getSHAPassword(DEFAULT_PASSWORD)

        user.password = encodedPassword

        userRepository.save(user)
    }

    fun deactivate(userId: Long) {
        val id = LongValueObject.valueOf(userId)
        id.validate()

        val user = userRepository.findById(id.value)
            .orElseThrow { EntityNotFoundException("Entidad usuario con ID $id no existe") }

        user.active = false

        userRepository.save(user)
    }

}
