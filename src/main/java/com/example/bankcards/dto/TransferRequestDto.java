package com.example.bankcards.dto;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


public record TransferRequestDto (
		@Size(min = 16, max = 16)
		String cardFrom,
		@Size(min = 16, max = 16)
		String cardTo,
		@Size(min = 1)
		BigDecimal amount) {

}
