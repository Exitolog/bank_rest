package com.example.bankcards.service;

import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.dto.PageCardsResponse;
import com.example.bankcards.entity.enums.StatusCard;

import java.util.UUID;

public interface CardService {

	CardResponse createCard(UUID userId);

	CardResponse findCardByCardNumber(String cardNumber);

	CardResponse findCardById(UUID id);

	void deleteCartById(UUID id);

	CardResponse changeCardStatus(UUID id, StatusCard statusCard);

	PageCardsResponse findMyCars(Integer page, Integer limot);
}
