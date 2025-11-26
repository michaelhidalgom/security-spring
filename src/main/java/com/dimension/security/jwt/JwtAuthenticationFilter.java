package com.dimension.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Se ejecuta por cada peticion, comprueba la validez (JwtTokenProvider) del token,
//permite el acceso al recurso, en caso contrario, lanza una excepcion
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	public JwtTokenProvider jwtTokenProvider;

	@Autowired
	public UserDetailsService jpaUserDetailsService;

	// Realiza un filtro a las peticiones recibidas para determinar si poseen token
	// y que acciones se deben realizar
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//obtenemos el token de la solicitud HTTP
		String token = obtenerJWTdeLaSolicitud(request);
		
		//validamos el token
		if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
			//obtenemos el username del token
			String username = jwtTokenProvider.obtenerUsernameDelJWT(token);
			
			//cargamos el usuario asociado al token
			UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			//Se inicia el SecurityContextHolder
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		//Continua con la cadena de ejecucion
		filterChain.doFilter(request, response);
	}

	//Bearer <token de acceso>
	private String obtenerJWTdeLaSolicitud(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7,bearerToken.length());
		}

		return null;
	}
}
