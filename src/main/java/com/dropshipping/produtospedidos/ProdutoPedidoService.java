package com.dropshipping.produtospedidos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dropshipping.enderecos.EnderecoRepository;
import com.dropshipping.exception.RegraNegocioException;
import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.pedidos.Pedido;
import com.dropshipping.pedidos.PedidoRepository;
import com.dropshipping.produtos.ProdutoRepository;
import com.dropshipping.service.MessagesService;

@Service
public class ProdutoPedidoService {
	public static final String PRODUTO_PEDIDO_NAO_ECONTRADO = "produtoPedido.naoEncontrado";
	public static final String PRODUTO_NAO_CADASTRADO = "produto.naoEncontrado";
	public static final String PEDIDO_NAO_CADASTRADO = "pedido.naoEncontrado";
	public static final String QUANTIDADE_ZERO = "quantidade.zero";

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired 
	ProdutoPedidoRepository produtoPedidoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	
	@Autowired
	MessagesService messages;
	
	public ProdutoPedido create(ProdutoPedido produtoPedido) throws RegraNegocioException{
		validaProdutoPedido(produtoPedido);
		
		return produtoPedidoRepository.save(produtoPedido);
	}

	public ProdutoPedido update(ProdutoPedido produtoPedido) throws RegraNegocioException, SampleEntityNotFoundException {
		Optional<ProdutoPedido> existing = produtoPedidoRepository.findById(produtoPedido.getId());
		validaProdutoPedido(produtoPedido);
		if (existing.isPresent()) {
			return produtoPedidoRepository.save(produtoPedido);
		} else {
			throw new SampleEntityNotFoundException(messages.get(PRODUTO_PEDIDO_NAO_ECONTRADO));
		}
	}

	public void delete(Integer id) throws SampleEntityNotFoundException {
		try {
			produtoPedidoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new SampleEntityNotFoundException(messages.get(PRODUTO_PEDIDO_NAO_ECONTRADO));
		}
	}

	public ProdutoPedido findById(Integer id) throws SampleEntityNotFoundException {
		Optional<ProdutoPedido> t = produtoPedidoRepository.findById(id);
		if (t.isPresent()) {
			return t.get();
		} else {
			throw new SampleEntityNotFoundException(messages.get(PRODUTO_PEDIDO_NAO_ECONTRADO));
		}
	}
	
	public List<ProdutoPedido> findByPedido(Integer id) throws SampleEntityNotFoundException {
		Pedido pedido = new Pedido();
		pedido.setId(id);
		List<ProdutoPedido> t = produtoPedidoRepository.findByPedido(pedido);
		if (t.isEmpty()) {
			throw new SampleEntityNotFoundException(messages.get(PRODUTO_PEDIDO_NAO_ECONTRADO));
		}
		return t;
	}

	public List<ProdutoPedido> getAll() {
		return produtoPedidoRepository.findAll();
	}
	
	public void validaProdutoPedido(ProdutoPedido produtoPedido) throws RegraNegocioException {
		if (!produtoRepository.findById(produtoPedido.getProduto().getId()).isPresent()) {
			throw new RegraNegocioException(messages.get(PRODUTO_NAO_CADASTRADO));
		}
		if (!pedidoRepository.findById(produtoPedido.getPedido().getId()).isPresent()) {
			throw new RegraNegocioException(messages.get(PEDIDO_NAO_CADASTRADO));
		}
		if(produtoPedido.getQuantidade() > 0) {
			throw new RegraNegocioException(messages.get(QUANTIDADE_ZERO));
		}
	}
}
