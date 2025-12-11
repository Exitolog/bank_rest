package com.example.bankcards.dto;

import java.math.BigDecimal;

public record CardResponse(
		String ownerUsername,
		String cardNumber,
		String expirationDate,
		String status,
		BigDecimal balance
		) {

	}
