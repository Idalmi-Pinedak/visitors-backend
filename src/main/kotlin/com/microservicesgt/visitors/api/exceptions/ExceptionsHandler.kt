package com.microservicesgt.visitors.api.exceptions

import com.microservicesgt.visitors.domain.exceptions.EntityNotFoundException
import com.microservicesgt.visitors.domain.exceptions.InternalErrorException
import com.microservicesgt.visitors.domain.exceptions.UnauthorizedAccess
import com.microservicesgt.visitors.domain.exceptions.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class ExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(ValidationException::class)
    fun errorInternoExceptionHandler(ex: ValidationException): ResponseEntity<Map<String, String>> {
        val respuesta = mutableMapOf<String, String>()

        respuesta["titulo"] = "Solicitud incorrecta"
        respuesta["mensaje"] = ex.message ?: "La solicitud necesita datos que son requeridos"

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(respuesta)
    }

    @ResponseBody
    @ExceptionHandler(InternalErrorException::class)
    fun errorInternoExceptionHandler(ex: InternalErrorException): ResponseEntity<Map<String, String>> {
        val respuesta = mutableMapOf<String, String>()

        respuesta["titulo"] = "Error interno"
        respuesta["mensaje"] = "Ocurrio un error interno en la aplicacion"

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(respuesta)
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedAccess::class)
    fun errorInternoExceptionHandler(ex: UnauthorizedAccess): ResponseEntity<Map<String, String>> {
        val respuesta = mutableMapOf<String, String>()

        respuesta["titulo"] = "Acceso no autorizado"
        respuesta["mensaje"] = ex.message ?: "No tiene acceso a este recurso"

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(respuesta)
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException::class)
    fun errorInternoExceptionHandler(ex: EntityNotFoundException): ResponseEntity<Map<String, String>> {
        val respuesta = mutableMapOf<String, String>()

        respuesta["titulo"] = "Entidad no encontrada"
        respuesta["mensaje"] = ex.message ?: "La entidad solicitada no existe"

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(respuesta)
    }


}
