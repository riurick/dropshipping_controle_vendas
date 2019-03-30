package com.dropshipping.historicos;

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
@RequestMapping("/api/v1/historicos")
@Api(value = "Historicos")
@CrossOrigin("*")
public class HistoricoController {
	
	public static final String HISTORICO_CRIADO = "historico.criado";
	public static final String HISTORICO_DELETADO = "historico.excluido";
	public static final String HISTORICO_ATUALIZADO = "historico.atualizado";
	
	@Autowired
	HistoricoService historicoService;
	
	@Autowired
	private MessagesService messages;
	
	@PostMapping
	@ApiOperation(value = "Cria um hitórico")
	public ResponseEntity<ServiceResponse<Historico>> create(@RequestBody @Valid  Historico historico) throws RegraNegocioException {

		historico = historicoService.create(historico);

		HttpHeaders headers = new HttpHeaders();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(historico.getId())
				.toUri();
		headers.setLocation(location);

		ServiceMessage message = new ServiceMessage(messages.get(HISTORICO_CRIADO));

		return new ResponseEntity<>(new ServiceResponse<>(historico, message), headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Detalha um historico pelo ID", notes = "Um ID válido deve ser informado", response = Historico.class)
	@GetMapping("/{id}")
	public ResponseEntity<ServiceResponse<Historico>> findById(@PathVariable Integer id) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(historicoService.findById(id)));
	}

	@GetMapping
	@ApiOperation(value = "Lista", response = Historico.class)
	public ServiceResponse<List<Historico>> lista() {
		return new ServiceResponse<>(historicoService.getAll());
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Altera os dados do historico informado", notes = "Um ID válido deve ser informado", response = Historico.class)
	public ResponseEntity<ServiceResponse<Historico>> update(@PathVariable Integer id,
			@Valid @RequestBody Historico historico) throws RegraNegocioException, SampleEntityNotFoundException {
		if (!historico.getId().equals(id)) {
			return new ResponseEntity<>(
					new ServiceResponse<>(null,
							new ServiceMessage(MessageType.ERROR, "URL ID: '" + id
									+ " historico não corresponde " + historico.getId() + "'.")),
					HttpStatus.BAD_REQUEST);
		}

		ServiceMessage message = new ServiceMessage(messages.get(HISTORICO_ATUALIZADO));

		return new ResponseEntity<>(new ServiceResponse<>(historicoService.update(historico), message),
				HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Apaga um historico pelo id", notes = "Um id válido deve ser informado", response = Historico.class)
	public ResponseEntity<ServiceResponse<Void>> delete(@PathVariable Integer id) throws SampleEntityNotFoundException {
		historicoService.delete(id);
		ServiceMessage message = new ServiceMessage(messages.get(HISTORICO_DELETADO));

		return new ResponseEntity<>(new ServiceResponse<>(message), HttpStatus.OK);
	}
}
