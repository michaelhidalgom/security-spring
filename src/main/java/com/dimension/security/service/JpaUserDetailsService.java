package com.dimension.security.service;

import com.dimension.security.model.Usuario;
import com.dimension.security.repository.IUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {  // ← El parámetro sigue siendo "username" por contrato de la interfaz

        Usuario usuario = usuarioDao.findByEmail(email)  // ← Cambiar de findByUsername a findByEmail
                .orElseThrow(() ->
                        {
                            logger.error("Error en el Login: no existe el email '" + email + "' en el sistema!");
                            return new UsernameNotFoundException("Email: " + email + " no existe en el sistema!");
                        }
                );

        return new User(usuario.getEmail(), usuario.getPassword(), usuario.getEnabled(),  // ← Cambiar a getEmail()
                true, true, true,
                Collections.emptyList());
    }
}