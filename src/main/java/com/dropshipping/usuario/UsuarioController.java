package com.dropshipping.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.response.ServiceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/usuario")
@Api(value = "Usuarios")
@CrossOrigin("*")	
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/login/{email}")
	@ApiOperation(value="Login de Usuario", notes = "Informar e-mail e senha válidos", response = Usuario.class)
	public ResponseEntity<ServiceResponse<Usuario>> login(@PathVariable String email) throws SampleEntityNotFoundException {
		return ResponseEntity.ok( new ServiceResponse<>(usuarioService.login(email)));
	}
}
