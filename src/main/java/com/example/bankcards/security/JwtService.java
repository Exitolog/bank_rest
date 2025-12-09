package com.example.bankcards.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Date;



@Service
@Slf4j
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long EXPIRATION;

	/**
	 * Генерируем JWT
	 */
	public String generateToken(String username) {
		return JWT.create()
				.withSubject(username)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
				.sign(Algorithm.HMAC256(secret));
	}

	/**
	 * Проверяем JWT на подлинность и издателя
	 */
	public String validateTokenGetLogin(String token) {

		if (token == null || token.isEmpty()) {
			return null;
		}

		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
					.build();

			DecodedJWT jwt = verifier.verify(token);

			return jwt.getSubject();
		} catch (JWTVerificationException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
			return null;
		}
	}
}
