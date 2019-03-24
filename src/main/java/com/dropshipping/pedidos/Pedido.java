package com.dropshipping.pedidos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.dropshipping.clientes.Cliente;
import com.dropshipping.vendedores.Vendedor;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
	private Date dtPedido;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cliente_id")
	@Fetch(FetchMode.JOIN)
	@NotNull
	private Cliente cliente;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="vendedor_id")
	@Fetch(FetchMode.JOIN)
	private Vendedor vendedor;
	
	private Boolean pagamentoEfetuado;
	
	@Size(max = 255)
	private String avComentario;
	
	private Integer nota;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(Date dtPedido) {
		this.dtPedido = dtPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Boolean getPagamentoEfetuado() {
		return pagamentoEfetuado;
	}

	public void setPagamentoEfetuado(Boolean pagamentoEfetuado) {
		this.pagamentoEfetuado = pagamentoEfetuado;
	}

	public String getAvComentario() {
		return avComentario;
	}

	public void setAvComentario(String avComentario) {
		this.avComentario = avComentario;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}
}
