package com.dropshipping.produtos;

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

import com.dropshipping.categorias.Categoria;
import com.dropshipping.fornecedores.Fornecedor;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@Size(max = 50)
	private String nome;
	
	@Size(max = 255)
	private String descricao;
	
	@Size(max = 50)
	private String marca;
	
	@NotNull
	private Double preco;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="categoria_id")
	@Fetch(FetchMode.JOIN)
	private Categoria categoria;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fornecedor_id")
	@Fetch(FetchMode.JOIN)
	@NotNull
	private Fornecedor fornecedor;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	
}
