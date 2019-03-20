package com.dropshipping;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dropshipping.usuario.Usuario;

public class MyUserPrincipal implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	
	public MyUserPrincipal(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return usuario.getSenha();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
