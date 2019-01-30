package com.dropshipping.pedidos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	@Query("Select p from Pedido p where p.cliente.id = :id ")
	Page<Pedido> buscaPorCliente(@Param("id") Integer id, Pageable pageable);
	
	@Query("Select p from Pedido p where p.vendedor.id = :id ")
	Page<Pedido> buscaPorVendedor(@Param("id") Integer id, Pageable pageable);
}
