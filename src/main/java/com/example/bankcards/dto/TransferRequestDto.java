package com.example.bankcards.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDto {

		@Size(min = 16, max = 16)
		private String cardFrom;
		@Size(min = 16, max = 16)
		private String cardTo;
		@Size(min = 1)
		private BigDecimal amount;

}
