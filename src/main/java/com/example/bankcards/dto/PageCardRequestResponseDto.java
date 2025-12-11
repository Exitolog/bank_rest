package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "Страница запросов на выпуск карт")
public class PageCardRequestResponseDto {

	@Schema(description = "Количество страниц в ответе", type = "integer", example = "1")
	private Integer totalPages;

	@Schema(description = "Количество элементов в ответе", type = "long", example = "1")
	private Long totalElements;

	@ArraySchema(schema = @Schema(description = "Список запросов на выпуск карт", type = "array", implementation = CardRequestResponseDto.class))
	List<CardRequestResponseDto> cardRequests;

}
