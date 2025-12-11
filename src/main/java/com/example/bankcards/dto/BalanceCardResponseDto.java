package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@Schema(description = "Баланс карты")
public class BalanceCardResponseDto {

		@Schema(description = "Номер карты", example = "1234 5678 9012 3456")
		private String cardNumber;
		@Schema(description = "Баланс карты", example = "1000.00")
		private BigDecimal balance;
}
