package com.example.bankcards.controller;

import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.dto.PageCardsResponse;
import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Schema(description = "Контроллер для работы с картами")
@RequestMapping("/cards")
public class CardController {

	private final CardService cardService;

	@PostMapping("/admin/{userId}")
	public CardResponse createCard(@PathVariable("userId") UUID userId) {
		return cardService.createCard(userId);
	}

	@GetMapping("/admin/{cardNumber}")
	public CardResponse findCardByCardNumber(@PathVariable String cardNumber) {
		return cardService.findCardByCardNumber(cardNumber);
	}

	@GetMapping("/admin/{id}")
	public CardResponse findCardById(@PathVariable UUID id) {
		return cardService.findCardById(id);
	}

	@PatchMapping("/admin/{id}/{status}")
	public CardResponse changeCardStatus(@PathVariable("id") UUID id, @PathVariable("status") StatusCard statusCard) {
		return cardService.changeCardStatus(id, statusCard);
	}

	@GetMapping("/myCards")
	public PageCardsResponse findMyCards(
			@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
			@RequestParam(defaultValue = "3", required = false, name = "limit") Integer limit) {
		return cardService.findMyCars(page, limit);
	}

}
