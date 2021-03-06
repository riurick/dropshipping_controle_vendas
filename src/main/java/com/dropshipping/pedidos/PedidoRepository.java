package com.dropshipping.pedidos;


import java.util.Date;

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
	
	@Query("Select p from Pedido p where p.nota is NOT NULL")
	Page<Pedido> buscaAvaliacoes(Pageable pageable);
	
	@Query("Select p from Pedido p where p.cliente.nome like %:nomeCliente% and (:dataInicio is null or :dataFim is null or p.dtPedido between :dataInicio and :dataFim) "
			+ "and p.vendedor.nome like %:nomeVendedor% and (:pago is null or p.pagamentoEfetuado = :pago) "
			+ "and p.avComentario like %:comentario% and (:nota is null or p.nota = :nota) ")
	Page<Pedido> filtra(@Param("nomeCliente") String nomeCliente, @Param("dataInicio") Date dataInicio , @Param("dataFim") Date dataFim, 
			@Param("nomeVendedor") String nomeVendedor, @Param("pago") Boolean pago,
			@Param("comentario") String comentario, @Param("nota") Integer nota,  Pageable pageable);
}
