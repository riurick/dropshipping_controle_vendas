package com.dropshipping.promocoespedidos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dropshipping.produtospedidos.ProdutoPedido;

public interface PromocaoPedidoRepository extends JpaRepository<PromocaoPedido, Integer>{
	
	
	Optional<ProdutoPedido> findByProdutoId(Integer id);

}
