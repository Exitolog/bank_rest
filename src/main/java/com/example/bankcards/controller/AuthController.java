package com.example.bankcards.controller;

import com.example.bankcards.dto.AuthRequest;
import com.example.bankcards.dto.AuthResponse;
import com.example.bankcards.dto.UserCreateRequestDto;
import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.exception.MyException;
import com.example.bankcards.security.JwtService;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth Controller", description = "Контролер для обработки аутентификации")
@Validated
public class AuthController {

//	private final AuthService authService;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;


//	@Operation(summary = "Эндпойнт для авторизации пользователя")
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "Авторизация прошла успешно")
//	})
//	@PostMapping(value = "/auth/signin")
//	public KeycloakTokenResponseDto signin(
//			@Validated
//			@RequestBody SigninRequestDto signinRequestDto,
//			HttpServletResponse response
//	) {
//		return authService.signin(signinRequestDto, response);
//	}

	@PostMapping("/registration")
	public UserResponseDto saveUser(@RequestBody UserCreateRequestDto dto) {
		return userService.saveUser(dto);
	}

	@Operation(summary = "Эндпойнт для авторизации пользователя")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Авторизация прошла успешно")
	})
	@PostMapping(value = "/auth/signin")
	public AuthResponse login(@RequestBody AuthRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.username(), request.password())
			);
		} catch (BadCredentialsException e) {
			throw new MyException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
		}

		String token = jwtService.generateToken(request.username());
		return new AuthResponse(token);
	}

}
