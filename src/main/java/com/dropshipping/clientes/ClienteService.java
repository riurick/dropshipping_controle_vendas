package com.dropshipping.clientes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dropshipping.enderecos.Endereco;
import com.dropshipping.enderecos.EnderecoRepository;
import com.dropshipping.exception.RegraNegocioException;
import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.service.MessagesService;
import com.dropshipping.util.ValidaEmail;
import com.dropshipping.util.ValidacaoCpfUtil;



@Service
public class ClienteService {
	
	public static final String CLIENTE_NAO_ECONTRADO = "cliente.naoEncontrado";
	public static final String CPF_JA_CADASTRADO = "cpf.jaCadastrado";
	public static final String EMAIL_JA_CADASTRADO = "email.jaCadastrado";
	public static final String CPF_INVALIDO = "cpf.invalido";
	public static final String EMAIL_INVALIDO = "email.invalido";

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	
	@Autowired
	MessagesService messages;
	
	public Cliente create(Cliente cliente) throws RegraNegocioException{
		validaCliente(cliente);
		//Cadastrando endereço
		Endereco e = cliente.getEndereco();
		e = enderecoRepository.save(cliente.getEndereco());
		cliente.setEndereco(e);
		return clienteRepository.save(cliente);
	}

	public Cliente update(Cliente cliente) throws RegraNegocioException, SampleEntityNotFoundException {
		Optional<Cliente> existing = clienteRepository.findById(cliente.getId());
		validaCliente(cliente);
		if (existing.isPresent()) {
			enderecoRepository.save(cliente.getEndereco());
			return clienteRepository.save(cliente);
		} else {
			throw new SampleEntityNotFoundException(messages.get(CLIENTE_NAO_ECONTRADO));
		}
	}

	public void delete(Integer id) throws SampleEntityNotFoundException {
		try {
			clienteRepository.deleteById(id);
			enderecoRepository.delete(clienteRepository.findById(id).get().getEndereco());
		} catch (EmptyResultDataAccessException e) {
			throw new SampleEntityNotFoundException(messages.get(CLIENTE_NAO_ECONTRADO));
		}
	}

	public Cliente findById(Integer id) throws SampleEntityNotFoundException {
		Optional<Cliente> t = clienteRepository.findById(id);
		if (t.isPresent()) {
			return t.get();
		} else {
			throw new SampleEntityNotFoundException(messages.get(CLIENTE_NAO_ECONTRADO));
		}
	}

	public List<Cliente> getAll() {
		return clienteRepository.findAll();
	}
	
	public void validaCliente(Cliente cliente) throws RegraNegocioException {
		if (!clienteRepository.findByCpf(cliente.getCpf().trim()).isEmpty()) {
			throw new RegraNegocioException(messages.get(CPF_JA_CADASTRADO));
		}
		if (!clienteRepository.findByEmail(cliente.getEmail().trim()).isEmpty()) {
			throw new RegraNegocioException(messages.get(EMAIL_JA_CADASTRADO));
		}
		if(!ValidacaoCpfUtil.isValidCPF(cliente.getCpf())) {
			throw new RegraNegocioException(messages.get(CPF_INVALIDO));
		}
		if(!ValidaEmail.validar(cliente.getEmail())) {
			throw new RegraNegocioException(messages.get(EMAIL_INVALIDO));
		}
	}

}
