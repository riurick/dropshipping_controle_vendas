package com.dropshipping.pedidos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dropshipping.clientes.ClienteRepository;
import com.dropshipping.enderecos.EnderecoRepository;
import com.dropshipping.exception.RegraNegocioException;
import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.service.MessagesService;
import com.dropshipping.vendedores.VendedorRepository;

@Service
public class PedidoService {
	public static final String CLIENTE_NAO_ECONTRADO = "cliente.naoEncontrado";
	public static final String VENDEDOR_NAO_ECONTRADO = "vendedor.naoEncontrado";
	public static final String PEDIDO_NAO_ECONTRADO = "pedido.naoEncontrado";

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	VendedorRepository vendedorRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	
	@Autowired
	MessagesService messages;
	
	public Pedido create(Pedido pedido) throws RegraNegocioException{
		validaPedido(pedido);
		return pedidoRepository.save(pedido);
	}

	public Pedido update(Pedido pedido) throws RegraNegocioException, SampleEntityNotFoundException {
		Optional<Pedido> existing = pedidoRepository.findById(pedido.getId());
		if (existing.isPresent()) {
			validaPedido(pedido);
			return pedidoRepository.save(pedido);
		} else {
			throw new SampleEntityNotFoundException(messages.get(PEDIDO_NAO_ECONTRADO));
		}
	}

	public void delete(Integer id) throws SampleEntityNotFoundException {
		try {
			pedidoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new SampleEntityNotFoundException(messages.get(PEDIDO_NAO_ECONTRADO));
		}
	}

	public Pedido findById(Integer id) throws SampleEntityNotFoundException {
		Optional<Pedido> t = pedidoRepository.findById(id);
		if (t.isPresent()) {
			return t.get();
		} else {
			throw new SampleEntityNotFoundException(messages.get(PEDIDO_NAO_ECONTRADO));
		}
	}

	public List<Pedido> getAll() {
		return pedidoRepository.findAll();
	}
	
	public void validaPedido(Pedido pedido) throws RegraNegocioException {
		if (!clienteRepository.findById(pedido.getCliente().getId()).isPresent()) {
			throw new RegraNegocioException(messages.get(CLIENTE_NAO_ECONTRADO));
		}
		if (pedido.getVendedor()!=null) {
			if(!vendedorRepository.findById(pedido.getVendedor().getId()).isPresent()) {
				throw new RegraNegocioException(messages.get(VENDEDOR_NAO_ECONTRADO));
			}
		}
	}
}
