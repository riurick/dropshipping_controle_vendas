package com.dropshipping.clientes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	List<Cliente> findByCpf(String cpf);
	
	List<Cliente> findByEmail(String email);
}
