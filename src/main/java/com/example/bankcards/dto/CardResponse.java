package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "ДТО карты")
public class CardResponse {

		@Schema(description = "Username пользователя карты", example = "user1")
		private String ownerUsername;
		@Schema(description = "Номер карты", example = "1234 5678 9012 3456")
		private String cardNumber;
		@Schema(description = "Дата окончания срока действия карты", example = "2023-12-31")
		private String expirationDate;
		@Schema(description = "Статус карты", example = "ACTIVE")
		private String status;



	}
