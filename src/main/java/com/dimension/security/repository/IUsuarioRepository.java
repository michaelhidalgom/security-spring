package com.dimension.security.repository;

import com.dimension.security.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long>{

	public Optional<Usuario> findByUsername(String username);

	public Optional<Usuario> findByEmail(String email);  // ← Agregar este método

	public Optional<Usuario> findByUsernameOrEmail(String username, String email);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);
}