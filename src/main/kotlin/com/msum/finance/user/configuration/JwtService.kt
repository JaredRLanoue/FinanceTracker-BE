package com.msum.finance.user.configuration

import com.msum.finance.user.models.entity.UserEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Service
class JwtService {
    fun extractUsername(token: String): String? {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun generateToken(userDetails: UserEntity): String? {
        return generateToken(HashMap(), userDetails)
    }

    fun generateToken(extractClaims: Map<String, JvmType.Object>, userEntity: UserEntity): String? {
        return Jwts
            .builder()
            .setClaims(extractClaims)
            .setSubject(userEntity.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isValidToken(token: String, userEntity: UserEntity): Boolean {
        val username: String? = extractUsername(token)
        return(username == userEntity.username) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignInKey(): Key {
        val keyByte: ByteArray? = Decoders.BASE64.decode(Env.SECRET_KEY)
        return Keys.hmacShaKeyFor(keyByte)
    }
}
