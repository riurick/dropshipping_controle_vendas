package com.dropshipping.historicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dropshipping.exception.RegraNegocioException;
import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.pedidos.PedidoRepository;
import com.dropshipping.service.MessagesService;
import com.dropshipping.statuspedido.StatusPedidoRepository;

@Service
public class HistoricoService {
	
	public static final String STATUS_NAO_ECONTRADO = "status.naoEncontrado";
	public static final String PEDIDO_NAO_ECONTRADO = "pedido.naoEncontrado";
	public static final String HISTORICO_NAO_ECONTRADO = "historico.naoEncontrado";
	
	@Autowired
	HistoricoRepository historicoRepository;
	
	@Autowired 
	StatusPedidoRepository statusPedidoRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	MessagesService messages;
	
	public Historico create(Historico historico) throws RegraNegocioException{
		validaHistorico(historico);
		return historicoRepository.save(historico);
	}

	public Historico update(Historico historico) throws RegraNegocioException, SampleEntityNotFoundException {
		Optional<Historico> existing = historicoRepository.findById(historico.getId());
		if (existing.isPresent()) {
			validaHistorico(historico);
			return historicoRepository.save(historico);
		} else {
			throw new SampleEntityNotFoundException(messages.get(HISTORICO_NAO_ECONTRADO));
		}
	}

	public void delete(Integer id) throws SampleEntityNotFoundException {
		try {
			historicoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new SampleEntityNotFoundException(messages.get(HISTORICO_NAO_ECONTRADO));
		}
	}

	public Historico findById(Integer id) throws SampleEntityNotFoundException {
		Optional<Historico> t = historicoRepository.findById(id);
		if (t.isPresent()) {
			return t.get();
		} else {
			throw new SampleEntityNotFoundException(messages.get(HISTORICO_NAO_ECONTRADO));
		}
	}

	public List<Historico> getAll() {
		return historicoRepository.findAll();
	}
	
	public void validaHistorico(Historico historico) throws RegraNegocioException {
		if (!statusPedidoRepository.findById(historico.getStatusPedido().getId()).isPresent()) {
			throw new RegraNegocioException(messages.get(STATUS_NAO_ECONTRADO));
		}
		if(!pedidoRepository.findById(historico.getPedido().getId()).isPresent()) {
			throw new RegraNegocioException(messages.get(PEDIDO_NAO_ECONTRADO));
		}
	}
	
}
