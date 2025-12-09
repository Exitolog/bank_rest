package com.example.bankcards.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MyException extends RuntimeException {

	private final HttpStatus status;

	public MyException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public int getStatusCode() {
		return status.value();
	}

}
