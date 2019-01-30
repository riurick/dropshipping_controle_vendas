package com.dropshipping.produtos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dropshipping.exception.RegraNegocioException;
import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.service.MessagesService;

@Service
public class ProdutoService {
	public static final String PRODUTO_NAO_ECONTRADO = "produto.naoEncontrado";

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	MessagesService messages;
	
	public Produto create(Produto produto) throws RegraNegocioException{
		return produtoRepository.save(produto);
	}

	public Produto update(Produto produto) throws RegraNegocioException, SampleEntityNotFoundException {
		Optional<Produto> existing = produtoRepository.findById(produto.getId());
		if (existing.isPresent()) {
			return produtoRepository.save(produto);
		} else {
			throw new SampleEntityNotFoundException(messages.get(PRODUTO_NAO_ECONTRADO));
		}
	}

	public void delete(Integer id) throws SampleEntityNotFoundException {
		try {
			produtoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new SampleEntityNotFoundException(messages.get(PRODUTO_NAO_ECONTRADO));
		}
	}

	public Produto findById(Integer id) throws SampleEntityNotFoundException {
		Optional<Produto> t = produtoRepository.findById(id);
		if (t.isPresent()) {
			return t.get();
		} else {
			throw new SampleEntityNotFoundException(messages.get(PRODUTO_NAO_ECONTRADO));
		}
	}

	public List<Produto> getAll() {
		return produtoRepository.findAll();
	}
	
	public Page<Produto> findByFiltro(String nome, String descricao, String marca, Pageable pageable){
		return produtoRepository.findByFiltro(nome == null ? "" : nome, descricao == null ? "" : descricao,
				marca == null ? "" : marca, pageable);
	}

}
