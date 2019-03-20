package com.dropshipping;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dropshipping.usuario.Usuario;
import com.dropshipping.usuario.UsuarioRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		if(!usuario.isPresent()) {
			throw new UsernameNotFoundException(email);
		}
		MyUserPrincipal user = new MyUserPrincipal(usuario.get());
		return user;
	}

}
