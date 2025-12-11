package com.example.bankcards.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


	@RequiredArgsConstructor
	@Getter
	public enum Role {

		@Schema(description = "Пользователь")
		USER(2),
		@Schema(description = "Админ")
		ADMIN(1);

		private final int priority;

}
