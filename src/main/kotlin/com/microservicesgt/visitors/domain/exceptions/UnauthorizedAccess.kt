package com.microservicesgt.visitors.domain.exceptions

class UnauthorizedAccess(message: String = "No tiene acceso a este recurso") : Exception(message)
