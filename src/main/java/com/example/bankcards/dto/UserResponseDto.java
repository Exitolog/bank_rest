package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Schema(description = "Данные о созданном пользователе")
public class UserResponseDto {

	@Schema(description = "Логин пользователя")
	private String username;
}
