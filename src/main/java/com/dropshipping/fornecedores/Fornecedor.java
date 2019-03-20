package com.dropshipping.fornecedores;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.dropshipping.enderecos.Endereco;
import com.dropshipping.usuario.Usuario;

@Entity
public class Fornecedor extends Usuario{

	@NotNull
	@Size(max = 14)
	private String cnpj;
	
	@NotNull
	@Size(max = 255)
	private String razaoSocial;
	
	@NotNull
	@Size(max = 255)
	private String nomeFantasia;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="endereco_id")
	@Fetch(FetchMode.JOIN)
	@NotNull
	private Endereco endereco;
	
	@Size(max = 12)
	private String telefone;
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
