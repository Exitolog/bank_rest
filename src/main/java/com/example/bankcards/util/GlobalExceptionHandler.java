package com.example.bankcards.util;

import com.example.bankcards.exception.ErrorResponse;
import com.example.bankcards.exception.MyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MyException.class)
	public ResponseEntity<ErrorResponse> handleMyException(MyException ex) {
		ErrorResponse error = new ErrorResponse(
				ex.getStatusCode(),
				ex.getStatus().getReasonPhrase(),
				ex.getMessage(),
				LocalDateTime.now()
		);
		return new ResponseEntity<>(error, ex.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse error = new ErrorResponse(
				500,
				"Internal Server Error",
				ex.getMessage(),
				LocalDateTime.now()
		);
		return ResponseEntity.status(500).body(error);
	}
}