package com.microservicesgt.visitors.application.utils

import com.microservicesgt.visitors.domain.exceptions.InternalErrorException
import java.security.MessageDigest

/**
 * Clase para convertir los passwords a un HASH SHA-256
 */
class SHA256Encrypt {

    private companion object {
        const val HEX_CHARS = "0123456789ABCDEF"
    }

    fun getSHAPassword(plainText: String): String {
        if (plainText.isBlank()) {
            return ""
        }

        try {
            val bytes = MessageDigest
                .getInstance("SHA-256")
                .digest(plainText.toByteArray())
            val result = StringBuilder(bytes.size * 2)

            bytes.forEach {
                val i = it.toInt()
                result.append(HEX_CHARS[i shr 4 and 0x0f])
                result.append(HEX_CHARS[i and 0x0f])
            }

            return result.toString()
        } catch (e: Exception) {
            throw InternalErrorException("Error al generar hash SHA-256")
        }
    }

}
