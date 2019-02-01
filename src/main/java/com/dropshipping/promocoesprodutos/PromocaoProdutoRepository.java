package com.dropshipping.promocoesprodutos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromocaoProdutoRepository extends JpaRepository<PromocaoProduto, Integer>{
	
	@Query(value = "Select p from PromocaoProduto p where p.produto.id = id and p.promocao.validade >= GETDATE() ")
	List<PromocaoProduto> findByProdutoId(Integer id);

}
