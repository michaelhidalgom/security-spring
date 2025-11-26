package com.dimension.security;

import com.dimension.security.jwt.JwtAuthenticationEntryPoint;
import com.dimension.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity  //Permite crear una clase de seguridad personalizada
public class MainSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService jpaUserDetailsService;

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  /**
   * Genera un Bean PasswordEncoder (BCryptPasswordEncoder) para ser administrado
   * por el contenedor de Spring
   */
  @Bean
  public PasswordEncoder getEncoder(){

    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  // AuthenticationManager: Interfaz que delega la autenticación
  // al AuthenticationProvider apropiado.
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * Configura la fuente de datos que proporciona las credenciales
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(jpaUserDetailsService);
  }

  /**
   * Configura el acceso a las URLs de la aplicación
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf()
            .ignoringAntMatchers("/h2-console/**", "/api/**")
            .and()

            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeRequests()
            .antMatchers("/api/auth/**", "/h2-console/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .headers()
            .frameOptions().sameOrigin();

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}

