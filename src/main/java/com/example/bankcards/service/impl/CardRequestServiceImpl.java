package com.example.bankcards.service.impl;

import com.example.bankcards.dto.CardRequestResponseDto;
import com.example.bankcards.dto.PageCardRequestResponseDto;
import com.example.bankcards.entity.CardRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.MyException;
import com.example.bankcards.mapper.CardRequestMapper;
import com.example.bankcards.repository.CardRequestRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardRequestService;
import com.example.bankcards.util.StaticHelperClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class CardRequestServiceImpl implements CardRequestService {

	private final UserRepository userRepository;
	private final CardRequestRepository cardRequestRepository;
	private final CardRequestMapper CARD_REQUEST_MAPPER;

	@Override
	@Transactional
	public String createNewCardRequest() {

		String usernameAuth = StaticHelperClass.getAuthUserName();

		User user = userRepository.findByUsername(usernameAuth)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "User not found"));

		CardRequest cardRequest = CardRequest.builder()
				.user(user)
				.dateRequest(LocalDate.now())
				.build();

		cardRequestRepository.save(cardRequest);

		log.info("Create new card request with id: {} for user: {}", cardRequest.getId().toString(), usernameAuth);
		return "Create new card request with id: " + cardRequest.getId().toString();
	}

	@Override
	public CardRequestResponseDto findCardRequestById(UUID id) {

		CardRequest cardRequest = cardRequestRepository.findById(id)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "Card request not found"));

		return CARD_REQUEST_MAPPER.toCardRequestResponseDto(cardRequest);
	}

	@Override
	@Transactional
	public PageCardRequestResponseDto findAllCardRequest(Integer page, Integer limit) {

		Pageable pageable = PageRequest.of(page - 1, limit);

		Page<CardRequest> cardRequestsPage = cardRequestRepository.findAll(pageable);

		String userName = StaticHelperClass.getAuthUserName();

		log.info("Find all card requests with user: {}" , userName);

		return 	PageCardRequestResponseDto.builder()
				.totalPages(cardRequestsPage.getTotalPages())
				.totalElements(cardRequestsPage.getTotalElements())
				.cardRequests(cardRequestsPage.stream().map(CARD_REQUEST_MAPPER::toCardRequestResponseDto).toList())
				.build();
	}
}
