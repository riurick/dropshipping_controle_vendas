package com.dropshipping.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query("Select f from Usuario f where f.email like :email and f.senha like :senha")
	Optional<Usuario> login(String email, String senha);

	Optional<Usuario> findByEmail(String email);

}
