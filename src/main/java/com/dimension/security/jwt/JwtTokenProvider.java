package com.dimension.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

// Genera el token y valida la correcta formacion del token
@Component
public class JwtTokenProvider {

	private final static Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;

	// Recibe la informacion (Authentication) del usuario autenticado y
	// genera el token que se otorgara al cliente
	public String generarToken(Authentication authentication) {

		String username = authentication.getName();
		Date fechaActual = new Date();

		//Rango de tiempo que durara el token
		Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);

		//Genera el valor del token
		String token = Jwts.builder()
				.setSubject(username)
				//setIssuedAt: fecha de generacion del token
				.setIssuedAt(new Date())
				.setExpiration(fechaExpiracion)
				//jwtSecret: llave secreta con la que se firma el token
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();

		return token;
	}

	// Obtiene el nombre de usuario del token
	public String obtenerUsernameDelJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	// Valida el token JWT
	public boolean validarToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

			return true;
		} catch (MalformedJwtException e) {
			logger.error("Token JWT no valido");
		} catch (UnsupportedJwtException e) {
			logger.error("Token JWT no compatible");
		} catch (ExpiredJwtException e) {
			logger.error("token expirado");
		} catch (IllegalArgumentException e) {
			logger.error("La cadena claims JWT esta vacia");
		} catch (SignatureException e) {
			logger.error("Token JWT caducado");
		}
		return false;
	}
}

