package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@Schema(description = "Страница пользователей")
public class PageUsersResponse {

	@Schema(description = "Количество страниц в ответе", type = "integer", example = "1")
	private  Integer totalPages;

	@Schema(description = "Количество элементов в ответе", type = "long", example = "1")
	private  Long totalElements;

	@ArraySchema(schema = @Schema(description = "Список всех пользователей", type = "array", implementation = UserResponseDto.class))
	private List<UserResponseDto> users;
}
