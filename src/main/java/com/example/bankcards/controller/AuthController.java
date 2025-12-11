package com.example.bankcards.controller;

import com.example.bankcards.dto.AuthRequestDto;
import com.example.bankcards.dto.AuthResponseDto;
import com.example.bankcards.dto.UserCreateRequestDto;
import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.exception.MyException;
import com.example.bankcards.security.JwtService;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Schema(description = "Контролер для обработки аутентификации")
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@Operation(summary = "Эндпойнт для регистрации пользователя")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован")
	})
	@PostMapping("/registration")
	public UserResponseDto saveUser(@RequestBody UserCreateRequestDto dto) {
		return userService.saveUser(dto);
	}

	@Operation(summary = "Эндпойнт для авторизации пользователя")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Авторизация прошла успешно")
	})
	@PostMapping(value = "/auth/signin")
	public AuthResponseDto login(@RequestBody AuthRequestDto request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.username(), request.password())
			);
		} catch (BadCredentialsException e) {
			throw new MyException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
		}

		String token = jwtService.generateToken(request.username());
		return new AuthResponseDto(token);
	}

}
