package com.dropshipping.produtos;

import java.net.URI;
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
@RequestMapping("/api/v1/produto")
@Api(value = "Produtos")
public class ProdutoController {
	
	public static final String PRODUTO_CRIADO = "produto.criado";
	public static final String PRODUTO_ATUALIZADO = "produto.atualizado";
	public static final String PRODUTO_DELETADO = "produto.excluido";
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	private MessagesService messages;
	
	@PostMapping
	@ApiOperation(value = "Cria um produto")
	public ResponseEntity<ServiceResponse<Produto>> create(@RequestBody @Valid Produto produto) throws RegraNegocioException {

		produto = produtoService.create(produto);

		HttpHeaders headers = new HttpHeaders();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.getId())
				.toUri();
		headers.setLocation(location);

		ServiceMessage message = new ServiceMessage(messages.get(PRODUTO_CRIADO));

		return new ResponseEntity<>(new ServiceResponse<>(produto, message), headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Detalha um produto pelo ID", notes = "Um ID válido deve ser informado", response = Produto.class)
	@GetMapping("/{id}")
	public ResponseEntity<ServiceResponse<Produto>> findById(@PathVariable Integer id) throws SampleEntityNotFoundException {
		return ResponseEntity.ok(new ServiceResponse<>(produtoService.findById(id)));
	}

	@GetMapping
	@ApiOperation(value = "Lista", response = Produto.class)
	public ServiceResponse<List<Produto>> listassuntosPaginado() {
		return new ServiceResponse<>(produtoService.getAll());
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Altera os dados do produto informado", notes = "Um ID válido deve ser informado", response = Produto.class)
	public ResponseEntity<ServiceResponse<Produto>> update(@PathVariable Integer id,
			@Valid @RequestBody Produto produto) throws RegraNegocioException, SampleEntityNotFoundException {
		if (!produto.getId().equals(id)) {
			return new ResponseEntity<>(
					new ServiceResponse<>(null,
							new ServiceMessage(MessageType.ERROR, "URL ID: '" + id
									+ " Produto não corresponde " + produto.getId() + "'.")),
					HttpStatus.BAD_REQUEST);
		}

		ServiceMessage message = new ServiceMessage(messages.get(PRODUTO_ATUALIZADO));

		return new ResponseEntity<>(new ServiceResponse<>(produtoService.update(produto), message),
				HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Apaga um Produto pelo id", notes = "Um id válido deve ser informado", response = Produto.class)
	public ResponseEntity<ServiceResponse<Void>> deleteassunto(@PathVariable Integer id) throws SampleEntityNotFoundException {
		produtoService.delete(id);
		ServiceMessage message = new ServiceMessage(messages.get(PRODUTO_DELETADO));

		return new ResponseEntity<>(new ServiceResponse<>(message), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Pagina produto por filtro", response = Produto.class)
	@GetMapping("/filtro")
	public ServiceResponse<Page<Produto>> findByFiltro(@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "marca", required = false) String marca, Pageable pageable) throws SampleEntityNotFoundException {
		Page<Produto> page = produtoService.findByFiltro(nome, descricao, marca, pageable);
		return new ServiceResponse<>(page);
	}
}
