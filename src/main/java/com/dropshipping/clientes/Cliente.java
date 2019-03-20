package com.dropshipping.clientes;

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
public class Cliente extends Usuario{
	
	@NotNull
	@Size(max = 50)
	private String nome;
	
	@Size(max = 12)
	private String telefone;
	
	@NotNull
	@Size(max = 11)
	private String cpf;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="endereco_id")
	@Fetch(FetchMode.JOIN)
	@NotNull
	private Endereco endereco;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
}
