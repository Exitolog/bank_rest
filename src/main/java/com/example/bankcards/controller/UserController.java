package com.example.bankcards.controller;

import com.example.bankcards.dto.UserCreateRequestDto;
import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Schema(description = "Контроллер для работы с пользователями")
public class UserController {

	private final UserService userService;

	@GetMapping("/all")
	public List<UserResponseDto> findAllUsers() {
		return userService.findAllUsers();
	}

	@GetMapping("/{id}")
	public UserResponseDto findByUserId(@PathVariable UUID id) {
		return userService.findUserById(id);
	}

	@GetMapping("/{username}")
	public UserResponseDto findByUsername(@PathVariable String username) {
		return userService.findUserByUsername(username);
	}

}
