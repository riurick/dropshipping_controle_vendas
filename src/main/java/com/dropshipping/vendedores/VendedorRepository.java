package com.dropshipping.vendedores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VendedorRepository extends JpaRepository<Vendedor, Integer>{
	List<Vendedor> findByCpf(String cpf);
	
	List<Vendedor> findByEmail(String email);
}
