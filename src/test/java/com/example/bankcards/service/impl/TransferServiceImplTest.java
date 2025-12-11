package com.example.bankcards.service.impl;

import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.util.StaticHelperClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

		@Mock
		private TransferRepository transferRepository;

		@Mock
		private CardRepository cardRepository;

		@InjectMocks
		private TransferServiceImpl transferService;

	@Test
	void transfer_shouldCompleteSuccessfully_whenValidData() {

		String cardFromNumber = "1111-2222-3333-4444";
		String cardToNumber = "5555-6666-7777-8888";
		BigDecimal amount = new BigDecimal("100.00");
		String username = "Mark";

		Card cardFrom = Card.builder().cardNumber(cardFromNumber).balance(new BigDecimal("500.00")).owner(User.builder().username(username).build()).build();

		Card cardTo = Card.builder().cardNumber(cardToNumber).balance(new BigDecimal("200.00")).owner(User.builder().username("jane_doe").build()).build();

		TransferRequestDto requestDto = new TransferRequestDto(cardFromNumber, cardToNumber, amount);

		try (MockedStatic<StaticHelperClass> mocked = Mockito.mockStatic(StaticHelperClass.class)) {
			mocked.when(StaticHelperClass::getAuthUserName).thenReturn(username);

			when(cardRepository.findByCardNumber(cardFromNumber)).thenReturn(Optional.of(cardFrom));
			when(cardRepository.findByCardNumber(cardToNumber)).thenReturn(Optional.of(cardTo));

			String result = transferService.transfer(requestDto);

			assertThat(result).isEqualTo("Transfer successful");

			assertThat(cardFrom.getBalance()).isEqualByComparingTo("400.00");
			assertThat(cardTo.getBalance()).isEqualByComparingTo("300.00");
		}
	}
}
