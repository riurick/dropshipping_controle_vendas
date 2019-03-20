package com.dropshipping.clientes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	List<Cliente> findByCpf(String cpf);
	
	Optional<Cliente> findByEmail(String email);
}
