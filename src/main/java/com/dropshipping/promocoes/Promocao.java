package com.dropshipping.promocoes;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Promocao {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Size(max = 32)
	private String nome;
	
	@Size(max = 255)
	private String descricao;
	
	@NotNull
	private Integer desconto;
	
	@NotNull
	private Date validade;
}
