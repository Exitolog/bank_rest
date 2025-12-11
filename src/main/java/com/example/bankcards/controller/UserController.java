package com.example.bankcards.controller;

import com.example.bankcards.dto.PageUsersResponse;
import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Schema(description = "Контроллер для работы с пользователями")
public class UserController {

	private final UserService userService;

	@Operation(summary = "Получение всех пользователей")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Получены пользователи",
			content = @Content(schema = @Schema(implementation = PageUsersResponse.class))))
	@GetMapping("/all")
	public PageUsersResponse findAllUsers(
			@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
			@RequestParam(defaultValue = "3", required = false, name = "limit") Integer limit) {
		return userService.findAllUsers(page,limit);
	}

	@Operation(summary = "Получение пользователя по id")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Пользователь получен",
			content = @Content(schema = @Schema(implementation = UserResponseDto.class))))
	@GetMapping("/by-id/{id}")
	public UserResponseDto findByUserId(@PathVariable UUID id) {
		return userService.findUserById(id);
	}

	@Operation(summary = "Получение пользователя по username")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Пользователь получен",
			content = @Content(schema = @Schema(implementation = UserResponseDto.class))))
	@GetMapping("/by-username/{username}")
	public UserResponseDto findByUsername(@PathVariable String username) {
		return userService.findUserByUsername(username);
	}

}
