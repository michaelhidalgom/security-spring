package com.dimension.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;

	public String generarToken(Authentication authentication) {
		String username = authentication.getName();
		Date ahora = new Date();
		Date expiracion = new Date(ahora.getTime() + jwtExpirationInMs);

		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(ahora)
				.setExpiration(expiracion)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String obtenerUsernameDelJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}

	public boolean validarToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Token JWT mal formado");
		} catch (ExpiredJwtException e) {
			logger.error("Token JWT expirado");
		} catch (UnsupportedJwtException e) {
			logger.error("Token JWT no soportado");
		} catch (IllegalArgumentException e) {
			logger.error("Claims JWT vacío");
		} catch (SignatureException e) {
			logger.error("Firma JWT inválida");
		}
		return false;
	}
}
