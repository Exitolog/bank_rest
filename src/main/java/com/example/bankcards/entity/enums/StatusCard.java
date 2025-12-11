package com.example.bankcards.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCard {

	@Schema(description = "Активна")
	ACTIVE("Активна"),
	@Schema(description = "Заблокирована")
	BLOCKED("Заблокирована"),
	@Schema(description = "Истек срок")
	EXPIRED("Истек срок");

	private final String status;
}
