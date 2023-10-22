package com.microservicesgt.visitors.domain.exceptions

class InternalErrorException(message: String): Exception("Error interno en la aplicacion, $message")
