package com.microservicesgt.visitors.application.utils

import com.microservicesgt.visitors.domain.entities.User
import com.microservicesgt.visitors.domain.exceptions.UnauthorizedAccess
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*


@Component
class JwtUtil {

    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${app.jwt.private-key}")
    private lateinit var privateKey: String

    @Value("\${app.jwt.public-key}")
    private lateinit var publicKey: String

    private fun loadPublicKey(): RSAPublicKey {
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        val pKey = publicKey
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace(System.lineSeparator(), "")

        val encoded: ByteArray = Base64.getDecoder().decode(pKey)

        val keySpec = X509EncodedKeySpec(encoded)
        return (keyFactory.generatePublic(keySpec) as RSAPublicKey)
    }

    private fun loadPrivateKey(): RSAPrivateKey {
        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        val pKey = privateKey
            .replace("-----BEGIN RSA PRIVATE KEY-----", "")
            .replace("-----END RSA PRIVATE KEY-----", "")
            .replace(System.lineSeparator(), "")

        val encoded: ByteArray = Base64.getDecoder().decode(pKey)

        val keySpec = PKCS8EncodedKeySpec(encoded)
        return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
    }

    fun generateToken(user: User): String {
        val claims = Jwts.claims().setSubject(user.email)

        claims["userId"] = "${user.id}"
        claims["name"] = user.name

        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.RS256, loadPrivateKey())
            .compact()
    }

    fun getTokenClaims(token: String): Claims {
        try {
            return Jwts.parser()
                .setSigningKey(loadPublicKey())
                .parseClaimsJws(token)
                .body
        } catch (ex: Exception) {
            log.error("Error al decodificar token", ex)
            throw UnauthorizedAccess()
        }
    }

}
