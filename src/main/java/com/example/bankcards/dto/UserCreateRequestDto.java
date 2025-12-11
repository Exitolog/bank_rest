package com.example.bankcards.dto;

import com.example.bankcards.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Schema(description = "Запрос на создание пользователя")
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDto {

	@Schema(description = "Логин пользователя")
	private String username;

	@Schema(description = "Пароль пользователя")
	private String password;

	@Schema(description = "Роль пользователя")
	private Role role;

}
