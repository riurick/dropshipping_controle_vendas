package com.dropshipping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistroPossuiDependenciasException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegistroPossuiDependenciasException(String exception) {
		super(exception);
	}

}
