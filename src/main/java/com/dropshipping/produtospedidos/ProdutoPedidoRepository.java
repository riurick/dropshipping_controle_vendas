package com.dropshipping.produtospedidos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dropshipping.pedidos.Pedido;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Integer>{
	List<ProdutoPedido> findByPedido(Pedido pedido);
}
