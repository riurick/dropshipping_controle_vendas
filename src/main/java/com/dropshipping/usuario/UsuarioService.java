package com.dropshipping.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dropshipping.exception.SampleEntityNotFoundException;
import com.dropshipping.service.MessagesService;

@Service
public class UsuarioService {
	public static final String USUARIO_INVALIDO = "usuario.invalido";
	
	@Autowired 
	UsuarioRepository usuarioRepository;
	
	@Autowired
	MessagesService messages;
	
	public Usuario login(String email) throws SampleEntityNotFoundException {
		Optional<Usuario> op = usuarioRepository.findByEmail(email);
		if(!op.isPresent()) {
			throw new SampleEntityNotFoundException(messages.get(USUARIO_INVALIDO));
		}
		return op.get();
	}
}
