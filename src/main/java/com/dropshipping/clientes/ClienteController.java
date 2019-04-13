package com.dropshipping.clientes;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	

	@ApiOperation(value = "Detalha um cliente pelo ID", notes = "Um ID válido deve ser informado", response = Cliente.class)
	@GetMapping("/{id}")
	@CrossOrigin("*")
	public ResponseEntity<ServiceResponse<Cliente>> findById(@PathVariable Integer id) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(clienteService.findById(id)));
	}

	@GetMapping
	@CrossOrigin("*")
	@ApiOperation(value = "Lista", response = Cliente.class)
	public ServiceResponse<List<Cliente>> lista() {
		return new ServiceResponse<>(clienteService.getAll());
	}

	
	@ApiOperation(value = "Detalha um cliente pelo Email", notes = "Um email válido deve ser informado", response = Cliente.class)
	@GetMapping("/getByEmail/{email}")
	@CrossOrigin("*")
	public ResponseEntity<ServiceResponse<Cliente>> findByEmail(@PathVariable String email) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(clienteService.findByEmail(email)));
	}

}
