package com.dropshipping.produtospedidos;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dropshipping.exception.RegraNegocioException;
import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.response.MessageType;
import com.dropshipping.response.ServiceMessage;
import com.dropshipping.response.ServiceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/produtoPedido")
@Api(value = "ProdutosPedidos")
public class ProdutoPedidoController {
	
	@Autowired
	ProdutoPedidoService produtoPedidoService;
	
	@PostMapping
	@ApiOperation(value = "Cria um assunto")
	public ResponseEntity<ServiceResponse<ProdutoPedido>> create(@RequestBody @Valid  ProdutoPedido pedido) throws RegraNegocioException {

		pedido = produtoPedidoService.create(pedido);

		HttpHeaders headers = new HttpHeaders();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId())
				.toUri();
		headers.setLocation(location);

		return new ResponseEntity<>(new ServiceResponse<>(pedido), headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Detalha um ProdutoPedido pelo ID", notes = "Um ID válido deve ser informado", response = ProdutoPedido.class)
	@GetMapping("/{id}")
	@CrossOrigin("*")
	public ResponseEntity<ServiceResponse<ProdutoPedido>> findById(@PathVariable Integer id) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(produtoPedidoService.findById(id)));
	}
	
	@ApiOperation(value = "Detalha um ProdutoPedido pelo ID do Pedido", notes = "Um ID válido deve ser informado", response = ProdutoPedido.class)
	@GetMapping("/pedido/{id}")
	@CrossOrigin("*")
	public ResponseEntity<ServiceResponse<List<ProdutoPedido>>> findByPedido(@PathVariable Integer id) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(produtoPedidoService.findByPedido(id)));
	}

	@GetMapping
	@CrossOrigin("*")
	@ApiOperation(value = "Lista", response = ProdutoPedido.class)
	public ServiceResponse<List<ProdutoPedido>> lista() {
		return new ServiceResponse<>(produtoPedidoService.getAll());
	}

	@PutMapping("/{id}")
	@CrossOrigin("*")
	@ApiOperation(value = "Altera os dados do ProdutoPedido informado", notes = "Um ID válido deve ser informado", response = ProdutoPedido.class)
	public ResponseEntity<ServiceResponse<ProdutoPedido>> update(@PathVariable Integer id,
			@Valid @RequestBody ProdutoPedido pedido) throws RegraNegocioException, SampleEntityNotFoundException {
		if (!pedido.getId().equals(id)) {
			return new ResponseEntity<>(
					new ServiceResponse<>(null,
							new ServiceMessage(MessageType.ERROR, "URL ID: '" + id
									+ " prodrutoPedido não corresponde " + pedido.getId() + "'.")),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ServiceResponse<>(produtoPedidoService.update(pedido)),
				HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	@CrossOrigin("*")
	@ApiOperation(value = "Apaga um ProdutoPedido pelo id", notes = "Um id válido deve ser informado", response = ProdutoPedido.class)
	public ResponseEntity<ServiceResponse<Void>> delete(@PathVariable Integer id) throws SampleEntityNotFoundException {
		produtoPedidoService.delete(id);

		return new ResponseEntity<>(new ServiceResponse<>(), HttpStatus.OK);
	}
	
	@GetMapping("/buscaPorCliente/{id}")
	@CrossOrigin("*")
	@ApiOperation(value = "Busca lista de ProdutoPedido pelo cliente", notes = "Um id válido deve ser informado", response = ProdutoPedido.class)
	public ServiceResponse<List<ProdutoPedido>> buscaPorCliente (@PathVariable Integer id) throws RegraNegocioException{
		return new ServiceResponse<>(produtoPedidoService.findByCliente(id));
	}

	@GetMapping("/filtra")
	@CrossOrigin("*")
	@ApiOperation(value = "Filtra ProdutoPedido", response = ProdutoPedido.class)
	public ServiceResponse<List<ProdutoPedido>> filtra(@RequestParam(value = "nomeCliente", required = false) String nomeCliente, 
			@RequestParam(value = "nomeProduto", required = false) String nomeProduto){
		return new ServiceResponse<>(produtoPedidoService.filtra(nomeCliente, nomeProduto));
	}
}
