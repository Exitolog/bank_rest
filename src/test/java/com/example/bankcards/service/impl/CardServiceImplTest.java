package com.example.bankcards.service.impl;

import com.example.bankcards.dto.BalanceCardResponseDto;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.CardRequestRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

	@Mock
	private CardRepository cardRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CardRequestRepository cardRequestRepository;

	@Mock
	private CardMapper cardMapper;

	@InjectMocks
	private CardServiceImpl cardService;

	@Test
	void findCardById_shouldReturnCardResponse_whenCardExists() {

		UUID cardId = UUID.randomUUID();

		Card card = Card.builder()
				.id(cardId)
				.cardNumber("1234-5678-9012-3456")
				.statusCard(StatusCard.ACTIVE)
				.balance(BigDecimal.TEN)
				.build();

		CardResponse expectedResponse = CardResponse.builder()
				.ownerUsername("Alex")
				.cardNumber(card.getCardNumber())
				.expirationDate(LocalDate.now().toString())
				.status(card.getStatusCard().getStatus())
				.build();


		when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
		when(cardMapper.toCardResponse(card)).thenReturn(expectedResponse);

		CardResponse result = cardService.findCardById(cardId);

		assertThat(result).isEqualTo(expectedResponse);
		verify(cardRepository).findById(cardId);
		verify(cardMapper).toCardResponse(card);
	}

	@Test
	void findBalanceByCardNumber_shouldReturnBalanceDto_whenCardExists() {

		String cardNumber = "1234-5678-9012-3456";
		BigDecimal balance = new BigDecimal("1000.50");

		Card card = Card.builder()
				.cardNumber(cardNumber)
				.balance(balance)
				.build();

		BalanceCardResponseDto expectedDto = BalanceCardResponseDto.builder()
				.cardNumber(cardNumber)
				.balance(balance)
				.build();

		when(cardRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));
		when(cardMapper.toBalanceCardResponseDto(card)).thenReturn(expectedDto);

		BalanceCardResponseDto result = cardService.findBalanceByCardNumber(cardNumber);

		assertThat(result).isEqualTo(expectedDto);
		verify(cardRepository).findByCardNumber(cardNumber);
		verify(cardMapper).toBalanceCardResponseDto(card);
	}
}
