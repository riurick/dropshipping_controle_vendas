package com.dropshipping.clientes;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dropshipping.exception.RegraNegocioException;
import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.response.MessageType;
import com.dropshipping.response.ServiceMessage;
import com.dropshipping.response.ServiceResponse;
import com.dropshipping.service.MessagesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/clientes")
@Api(value = "Clientes")
public class ClienteController {
	
	public static final String CLIENTE_CRIADO = "cliente.criado";
	public static final String CLIENTE_ATUALIZADO = "cliente.atualizado";
	public static final String CLIENTE_DELETADO = "cliente.deletado";
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	private MessagesService messages;
	
	@PostMapping
	@ApiOperation(value = "Cria um cliente")
	public ResponseEntity<ServiceResponse<Cliente>> create(@RequestBody @Valid Cliente cliente) throws RegraNegocioException {

		cliente = clienteService.create(cliente);

		HttpHeaders headers = new HttpHeaders();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		headers.setLocation(location);

		ServiceMessage message = new ServiceMessage(messages.get(CLIENTE_CRIADO));

		return new ResponseEntity<>(new ServiceResponse<>(cliente, message), headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Detalha um cliente pelo ID", notes = "Um ID válido deve ser informado", response = Cliente.class)
	@GetMapping("/{id}")
	public ResponseEntity<ServiceResponse<Cliente>> findById(@PathVariable Integer id) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(clienteService.findById(id)));
	}

	@GetMapping
	@ApiOperation(value = "Lista", response = Cliente.class)
	public ServiceResponse<List<Cliente>> lista() {
		return new ServiceResponse<>(clienteService.getAll());
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Altera os dados do cliente informado", notes = "Um ID válido deve ser informado", response = Cliente.class)
	public ResponseEntity<ServiceResponse<Cliente>> update(@PathVariable Integer id,
			@Valid @RequestBody Cliente cliente) throws RegraNegocioException, SampleEntityNotFoundException {
		if (!cliente.getId().equals(id)) {
			return new ResponseEntity<>(
					new ServiceResponse<>(null,
							new ServiceMessage(MessageType.ERROR, "URL ID: '" + id
									+ " CLiente não corresponde " + cliente.getId() + "'.")),
					HttpStatus.BAD_REQUEST);
		}

		ServiceMessage message = new ServiceMessage(messages.get(CLIENTE_ATUALIZADO));

		return new ResponseEntity<>(new ServiceResponse<>(clienteService.update(cliente), message),
				HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Apaga um cliente pelo id", notes = "Um id válido deve ser informado", response = Cliente.class)
	public ResponseEntity<ServiceResponse<Void>> delete(@PathVariable Integer id) throws SampleEntityNotFoundException {
		clienteService.delete(id);
		ServiceMessage message = new ServiceMessage(messages.get(CLIENTE_DELETADO));

		return new ResponseEntity<>(new ServiceResponse<>(message), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Detalha um cliente pelo Email", notes = "Um email válido deve ser informado", response = Cliente.class)
	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<ServiceResponse<Cliente>> findByEmail(@PathVariable String email) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(clienteService.findByEmail(email)));
	}

}
