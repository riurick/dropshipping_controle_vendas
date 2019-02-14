package com.dropshipping.fornecedores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer>{
	List<Fornecedor> findByCnpj(String cnpj);
	
}
