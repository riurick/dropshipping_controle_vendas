package com.dropshipping.pedidos;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/v1/pedidos")
@Api(value = "Pedidos")
public class PedidoController {
	public static final String PEDIDO_CRIADO = "pedido.criado";
	public static final String PEDIDO_ATUALIZADO = "pedido.atualizado";
	public static final String PEDIDO_DELETADO = "pedido.deletado";
	@Autowired
	 PedidoService pedidoService;
	
	@Autowired
	private MessagesService messages;
	
	@PostMapping
	@ApiOperation(value = "Cria um pedido")
	public ResponseEntity<ServiceResponse<Pedido>> create(@RequestBody @Valid  Pedido pedido) throws RegraNegocioException {

		pedido = pedidoService.create(pedido);

		HttpHeaders headers = new HttpHeaders();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId())
				.toUri();
		headers.setLocation(location);

		ServiceMessage message = new ServiceMessage(messages.get(PEDIDO_CRIADO));

		return new ResponseEntity<>(new ServiceResponse<>(pedido, message), headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Detalha um Pedido pelo ID", notes = "Um ID válido deve ser informado", response = Pedido.class)
	@GetMapping("/{id}")
	public ResponseEntity<ServiceResponse<Pedido>> findById(@PathVariable Integer id) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(pedidoService.findById(id)));
	}

	@GetMapping
	@ApiOperation(value = "Lista", response = Pedido.class)
	public ServiceResponse<List<Pedido>> listassuntosPaginado() {
		return new ServiceResponse<>(pedidoService.getAll());
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Altera os dados do Pedido informado", notes = "Um ID válido deve ser informado", response = Pedido.class)
	public ResponseEntity<ServiceResponse<Pedido>> update(@PathVariable Integer id,
			@Valid @RequestBody Pedido pedido) throws RegraNegocioException, SampleEntityNotFoundException {
		if (!pedido.getId().equals(id)) {
			return new ResponseEntity<>(
					new ServiceResponse<>(null,
							new ServiceMessage(MessageType.ERROR, "URL ID: '" + id
									+ " pedido não corresponde " + pedido.getId() + "'.")),
					HttpStatus.BAD_REQUEST);
		}

		ServiceMessage message = new ServiceMessage(messages.get(PEDIDO_ATUALIZADO));

		return new ResponseEntity<>(new ServiceResponse<>(pedidoService.update(pedido), message),
				HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Apaga um pedido pelo id", notes = "Um id válido deve ser informado", response = Pedido.class)
	public ResponseEntity<ServiceResponse<Void>> delete(@PathVariable Integer id) throws SampleEntityNotFoundException {
		pedidoService.delete(id);
		ServiceMessage message = new ServiceMessage(messages.get(PEDIDO_DELETADO));

		return new ResponseEntity<>(new ServiceResponse<>(message), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Detalha um Pedido pelo ID do cliente", notes = "Um ID válido deve ser informado", response = Pedido.class)
	@GetMapping("/cliente/{id}")
	public ResponseEntity<ServiceResponse<Page<Pedido>>> buscaPorCliente(@PathVariable Integer id, Pageable pageable) throws SampleEntityNotFoundException, RegraNegocioException {
		Page<Pedido> page = pedidoService.buscaPorCliente(id, pageable);
		return ResponseEntity.ok(new ServiceResponse<>(page));
	}
	
	@ApiOperation(value = "Detalha um Pedido pelo ID do vendedor", notes = "Um ID válido deve ser informado", response = Pedido.class)
	@GetMapping("/vendedor/{id}")
	public ResponseEntity<ServiceResponse<Page<Pedido>>> buscaPorVendedor(@PathVariable Integer id, Pageable pageable) throws SampleEntityNotFoundException, RegraNegocioException {
		Page<Pedido> page = pedidoService.buscaPorVendedor(id, pageable);
		return ResponseEntity.ok(new ServiceResponse<>(page));
	}
	
	@GetMapping("/buscaAvaliacoes")
	@ApiOperation(value = "Lista Pedidos com Avaliações", response = Pedido.class)
	public ResponseEntity<ServiceResponse<Page<Pedido>>> buscaAvaliacoes(Pageable pageable){
		return new ResponseEntity<>(new ServiceResponse<>(pedidoService.buscaAvaliacoes(pageable)), HttpStatus.OK);
	}
	
	@GetMapping("/filtra")
	@ApiOperation(value = "Filtra lista de Pedidos", response = Pedido.class)
	public ResponseEntity<ServiceResponse<Page<Pedido>>> filtra(
			@RequestParam(value = "nomeCliente", required = false) String nomeCliente,
			@RequestParam(value = "dataInicio", required = false) Date dataInicio,
			@RequestParam(value = "dataFim", required = false) Date dataFim,
			@RequestParam(value = "nomeVendedor", required = false) String nomeVendedor, 
			@RequestParam(value = "pago", required = false) Boolean pago,
			@RequestParam(value = "comentario", required = false) String comentario,
			@RequestParam(value = "nota", required = false) Integer nota, Pageable pageable){
		return new ResponseEntity<>(new ServiceResponse<>(pedidoService.filtra(nomeCliente, dataInicio, dataFim,
				nomeVendedor, pago, comentario, nota, pageable)), HttpStatus.OK);
	}

}
