package com.example.bankcards.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

	@Schema(description = "Активна")
	ACTIVE("Активна"),
	@Schema(description = "Заблокирована")
	BLOCKED("Заблокирована"),
	@Schema(description = "Истек срок")
	EXPIRED("Истек срок");

	private final String status;
}
