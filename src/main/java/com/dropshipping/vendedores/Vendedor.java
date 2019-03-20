package com.dropshipping.vendedores;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dropshipping.usuario.Usuario;

@Entity
public class Vendedor extends Usuario {
	
	@NotNull
	@Size(max = 50)
	private String nome;
	
	@Size(max = 12)
	private String telefone;
	
	@NotNull
	@Size(max = 11)
	private String cpf;
	
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

}
