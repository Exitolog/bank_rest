package com.example.bankcards.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatustCardRequest {


	@Schema(description = "Выпущена")
	OPEN("Выпущена"),
	@Schema(description = "Отказано в выпуске карты")
	BLOCKED("Отказано в выпуске карты"),
	@Schema(description = "В обработке")
	IN_PROGRESS("В обработке");

	private final String status;
}
