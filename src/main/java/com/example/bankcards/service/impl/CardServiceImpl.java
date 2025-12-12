package com.example.bankcards.service.impl;

import com.example.bankcards.dto.BalanceCardResponseDto;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.dto.PageCardsResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.exception.MyException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.CardRequestRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CardNumberEncryptor;
import com.example.bankcards.util.StaticHelperClass;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Schema(description = "Сервис для работы с картами")
@Slf4j
public class CardServiceImpl implements CardService {

	private final CardRepository cardRepository;
	private final UserRepository userRepository;
	private final CardNumberEncryptor cardNumberEncryptor;
	private final CardRequestRepository cardRequestRepository;
	private final CardMapper CARD_MAPPER;


	@Override
	@Transactional
	public CardResponse createCard(UUID userId, UUID requestId) {


		User owner = userRepository.findById(userId)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "User not found"));

		String cardNumber = generateCardNumber();

		Card card = Card.builder()
				.owner(owner)
				.cardNumber(cardNumber)
				.expirationDate(LocalDate.now().plusYears(3))
				.statusCard(StatusCard.ACTIVE)
				.balance(BigDecimal.ZERO)
				.build();

		Card savedCard = cardRepository.save(card);

		if(requestId != null) {
			try {
				cardRequestRepository.deleteById(requestId);
			} catch (Exception e) {
				log.error("Error deleting card request: {}", e.getMessage());
				throw new MyException(HttpStatus.NOT_FOUND, "Card request not found");
			}
		}
		return CARD_MAPPER.toCardResponse(savedCard);
	}

	@Override
	@Transactional
	public BalanceCardResponseDto findBalanceByCardNumber(String cardNumber) {

		Card card = cardRepository.findByCardNumber(cardNumber)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "Card not found"));

		String userAuth = StaticHelperClass.getAuthUserName();

		if(!card.getOwner().getUsername().equals(userAuth)) {
			throw new MyException(HttpStatus.FORBIDDEN, "Access denied");
		}

		return CARD_MAPPER.toBalanceCardResponseDto(card);

	}

	@Override
	public CardResponse findCardById(UUID id) {
		return CARD_MAPPER.toCardResponse(cardRepository.findById(id)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "Card not found")));
	}

	@Override
	@Transactional
	public void deleteCartById(UUID id) {
		cardRepository.deleteById(id);
	}

	@Override
	@Transactional
	public PageCardsResponse findAllCards(Integer page, Integer limit) {
		Pageable pageable = PageRequest.of(page - 1, limit);

		Page<Card> cardPage = cardRepository.findAll(pageable);

		return PageCardsResponse.builder()
				.totalPages(cardPage.getTotalPages())
				.totalElements(cardPage.getTotalElements())
				.cards(cardPage.stream().map(CARD_MAPPER::toCardResponse).toList())
				.build();
	}

	@Override
	@Transactional
	public CardResponse changeCardStatus(UUID id, StatusCard statusCard) {

		Card card = cardRepository.findById(id)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "Card not found"));

		card.setStatusCard(statusCard);
		cardRepository.save(card);


		log.info("Card status in card with id {} changed to {}",id, statusCard);

		return CARD_MAPPER.toCardResponse(card);
	}

	@Override
	@Transactional
	public PageCardsResponse findMyCards(Integer page, Integer limit) {

		String usernameAuth = StaticHelperClass.getAuthUserName();

		Pageable pageable = PageRequest.of(page - 1, limit);


		Page<Card> cardPage = cardRepository.findCardsByUsername(usernameAuth, pageable);

		return PageCardsResponse.builder()
				.totalPages(cardPage.getTotalPages())
				.totalElements(cardPage.getTotalElements())
				.cards(cardPage.stream().map(CARD_MAPPER::toCardResponse).toList())
				.build();
	}


	/**
	 * Генерирует валидный номер карты (16 цифр), 12 первых цифр - 4, остальные - случайные (уникальные)
	 */
	private String generateCardNumber() {
		while (true) {
			StringBuilder card = new StringBuilder();

			card.append("4".repeat(12));

			for (int i = 0; i <4; i++) {
				card.append((int) (Math.random() * 10));
			}

			String cardNumber = card.toString();

			try {
				if (!cardRepository.existsByCardNumber(cardNumberEncryptor.encrypt((cardNumber)))) {
					return cardNumber;
				}
			} catch (Exception e) {
				log.error("Ошибка при шифровании номера карты {}, сообщение: {}", e, e.getMessage());
				throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "Error encrypting card number");
			}
		}
	}
}
