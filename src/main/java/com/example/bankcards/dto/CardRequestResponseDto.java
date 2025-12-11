package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Schema(description = "Данные о запросе карты")
public class CardRequestResponseDto {

	@Schema(description = "Идентификатор запроса", example = "9c8a3b0b-7d2f-11ec-9a03-0242ac120002")
	private UUID id;

	@Schema(description = "Идентификатор пользователя, отправившего запрос", example = "9c8a3b0b-7d2f-11ec-9a03-0242ac120002")
	private UUID userId;

	@Schema(description = "Дата запроса", example = "2022-01-01")
	private LocalDate dateRequest;

}
