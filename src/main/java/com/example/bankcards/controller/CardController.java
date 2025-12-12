package com.example.bankcards.controller;

import com.example.bankcards.dto.BalanceCardResponseDto;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.dto.PageCardsResponse;
import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Schema(description = "Контроллер для работы с картами")
@RequestMapping("/cards")
public class CardController {

	private final CardService cardService;

	@Operation(summary = "Эндпойнт для выпуска карты пользователю")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Карта успешно создана",
					content = @Content(schema = @Schema(implementation = CardResponse.class)))
	})
	@PostMapping("/admin/{userId}")
	public CardResponse createCard(
			@PathVariable("userId") UUID userId,
			@RequestParam(value = "requestId", required = false) UUID requestId) {
		return cardService.createCard(userId, requestId);
	}

	@Operation(summary = "Получение баланса по номеру карты")
		@ApiResponses(@ApiResponse(responseCode = "200", description = "Получен баланс по карте",
				content = @Content(schema = @Schema(implementation = BalanceCardResponseDto.class))))
	@GetMapping
	public BalanceCardResponseDto findBalanceCardByCardNumber(@RequestParam("cardNumber") String cardNumber) {
		return cardService.findBalanceByCardNumber(cardNumber);
	}

	@Operation(summary = "Получение карты по id")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Получена карта",
			content = @Content(schema = @Schema(implementation = CardResponse.class))))
	@GetMapping("/admin/{id}")
	public CardResponse findCardById(@PathVariable UUID id) {
		return cardService.findCardById(id);
	}

	@Operation(summary = "Cмена статуса карты по id")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Статус карты изменен",
			content = @Content(schema = @Schema(implementation = CardResponse.class))))
	@PatchMapping("/admin/{id}/{status}")
	public CardResponse changeCardStatus(@PathVariable("id") UUID id, @PathVariable("status") StatusCard statusCard) {
		return cardService.changeCardStatus(id, statusCard);
	}

	@Operation(summary = "Получение всех карт авторизованного пользователя")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Получены все карты пользователя",
				content = @Content(schema = @Schema(implementation = PageCardsResponse.class))))
	@GetMapping("/myCards")
	public PageCardsResponse findMyCards(
			@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
			@RequestParam(defaultValue = "3", required = false, name = "limit") Integer limit) {
		return cardService.findMyCards(page, limit);
	}

	@Operation(summary = "Получение всех карт администратором")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "Получены все карты в системе",
			content = @Content(schema = @Schema(implementation = PageCardsResponse.class))))
	@GetMapping("/admin/allCards")
	public PageCardsResponse findAllCards(
			@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
			@RequestParam(defaultValue = "3", required = false, name = "limit") Integer limit) {
		return cardService.findAllCards(page, limit);
	}

	@Operation(summary = "Удаление карты по id")
	@ApiResponses(@ApiResponse(responseCode = "204", description = "Карта успешно удалена"))
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCartById(@PathVariable("id") UUID id) {
		cardService.deleteCartById(id);
	}
}
