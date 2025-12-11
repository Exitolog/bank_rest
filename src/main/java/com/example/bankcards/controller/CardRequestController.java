package com.example.bankcards.controller;

import com.example.bankcards.dto.CardRequestResponseDto;
import com.example.bankcards.dto.PageCardRequestResponseDto;
import com.example.bankcards.service.CardRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cardRequest")
@Schema(description = "Контроллер для запросов на выпуск карт")
public class CardRequestController {

	private final CardRequestService cardRequestService;

	@Operation(summary = "Создание запроса на выпуск карты.")
	@ApiResponses(value = {
				@ApiResponse(responseCode = "201", description = "Запрос на выпуск карты успешно создан",
						content = @Content(schema = @Schema(implementation = String.class)))})
	@PostMapping("/newCardRequest")
	public String createNewCardRequest() {
		return cardRequestService.createNewCardRequest();
	}

	@Operation(summary = "Получение запроса на выпуск карты по id")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Получен запрос нв выпуск карты",
			content = @Content(schema = @Schema(implementation = CardRequestResponseDto.class))))
	@GetMapping("/{id}")
	public CardRequestResponseDto findCardRequestById(@PathVariable("id") UUID id) {
		return cardRequestService.findCardRequestById(id);
	}

	@Operation(summary = "Получение всех запросов на выпуск карты")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Получены все запросы нв выпуск карты",
			content = @Content(schema = @Schema(implementation = PageCardRequestResponseDto.class))))
	@GetMapping("/all")
	public PageCardRequestResponseDto findAllCardRequest(
			@RequestParam(required = false, defaultValue = "1" ) Integer page,
			@RequestParam(required = false, defaultValue = "3" ) Integer limit) {
		return cardRequestService.findAllCardRequest(page, limit);
	}
}
