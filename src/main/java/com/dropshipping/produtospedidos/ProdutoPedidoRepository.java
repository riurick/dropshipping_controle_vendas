package com.dropshipping.produtospedidos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dropshipping.pedidos.Pedido;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Integer>{
	
	List<ProdutoPedido> findByPedido(Pedido pedido);
	
	@Query(value = "Select p from ProdutoPedid p where p.pedido.cliente.id = :id")
	List<ProdutoPedido> findByCliente (@Param("id") Integer id);
	
	@Query(value = "Select p from ProdutoPedido p where p.pedido.cliente.nome like %:nomeCliente% "
			+ "  and p.produto.nome like %:nomeProduto ")
	List<ProdutoPedido> filta(@Param("nomeCliente") String nomeCliente, @Param("nomeProduto") String nomeProduto);
}
