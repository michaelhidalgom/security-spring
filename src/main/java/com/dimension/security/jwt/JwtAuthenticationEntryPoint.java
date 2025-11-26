package com.dimension.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// AuthenticationEntryPoint:
// Las excepciones de seguridad de Spring se inician en el AuthenticationEntryPoint
// Comprueba si hay un token valido, en caso contrario retorna una respuesta 401
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	private static  Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	// commence:
	// Se llama a este método cada vez que se lanza una excepción debido a que
	// un usuario no autenticado intenta acceder a un recurso que requiere autenticación.
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		//logger.error("fail en el metodo commence");

		//SC_UNAUTHORIZED: 401
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
	}

}
