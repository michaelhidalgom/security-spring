package com.dimension.security.repository;

import com.dimension.security.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
}
