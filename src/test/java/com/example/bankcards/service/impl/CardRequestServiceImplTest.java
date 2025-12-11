package com.example.bankcards.service.impl;


import com.example.bankcards.dto.CardRequestResponseDto;
import com.example.bankcards.entity.CardRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.MyException;
import com.example.bankcards.mapper.CardRequestMapper;
import com.example.bankcards.repository.CardRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardRequestServiceImplTest {


	@Mock
	private CardRequestRepository cardRequestRepository;

	@Mock
	private CardRequestMapper cardRequestMapper;

	@InjectMocks
	private CardRequestServiceImpl cardRequestService;

	@Test
	void findCardRequestById_shouldReturnDto_whenCardRequestExists() {

		UUID id = UUID.randomUUID();
		User user = User.builder().id(UUID.randomUUID()).username("User222").build();
		CardRequest cardRequest = CardRequest.builder()
				.id(id)
				.user(user)
				.dateRequest(LocalDate.now())
				.build();

		CardRequestResponseDto expectedDto = CardRequestResponseDto.builder()
				.id(id)
				.userId(user.getId())
				.dateRequest(cardRequest.getDateRequest())
				.build();

		when(cardRequestRepository.findById(id)).thenReturn(Optional.of(cardRequest));
		when(cardRequestMapper.toCardRequestResponseDto(cardRequest)).thenReturn(expectedDto);

		CardRequestResponseDto result = cardRequestService.findCardRequestById(id);

		assertThat(result).isEqualTo(expectedDto);
		verify(cardRequestRepository).findById(id);
		verify(cardRequestMapper).toCardRequestResponseDto(cardRequest);
	}

	@Test
	void findCardRequestById_shouldThrowMyException_whenCardRequestNotFound() {

		UUID id = UUID.randomUUID();

		when(cardRequestRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> cardRequestService.findCardRequestById(id))
				.isInstanceOf(MyException.class)
				.hasMessage("Card request not found")
				.extracting("status")
				.isEqualTo(HttpStatus.NOT_FOUND);
	}

}
