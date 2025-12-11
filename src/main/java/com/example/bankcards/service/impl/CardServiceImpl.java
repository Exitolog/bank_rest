package com.example.bankcards.service.impl;

import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.dto.PageCardsResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.exception.MyException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CardNumberEncryptor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private final CardMapper CARD_MAPPER;


	@Override
	@Transactional
	public CardResponse  createCard(UUID userId) {

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

		return CARD_MAPPER.toCardResponse(savedCard);
	}

	@Override
	public CardResponse findCardByCardNumber(String cardNumber) {
		return CARD_MAPPER.toCardResponse(cardRepository.findByCardNumber(cardNumber)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "Card not found")));
	}

	@Override
	public CardResponse findCardById(UUID id) {
		return CARD_MAPPER.toCardResponse(cardRepository.findById(id)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "Card not found")));
	}

	@Override
	public void deleteCartById(UUID id) {

	}


//	Sort sort = Sort.by(
//			(Set.of("discountMin", "discountMax").contains(dto.getSortBy())
//					? List.of("discountMin", "discountMax")
//					: List.of(dto.getSortBy()))
//					.stream()
//					.map(field -> Sort.Order.by(field)
//							.with(Sort.Direction.valueOf(dto.getSortOrder().toUpperCase())))
//					.toList()
//	);
//
//	Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getLimit(), sort);
//
//		if (dto.getSlug().equalsIgnoreCase(SLUG_FUTURE)) {
//		log.info("Поиск будущих бенефитов в городе {}", city.getName());
//		Page<Benefit> benefits = benefitRepository.findAllBenefitsByStatusAndCityId(cityId, pageable, FUTURE);
//		return getPageBenefitsPreviewsDto(benefits, "Будущие бенефиты");
//	} else if (dto.getSlug().equalsIgnoreCase(SLUG_NEW)) {
//		log.info("Поиск новых бенефитов в городе {}", city.getName());
//		Page<Benefit> benefits = benefitRepository.findNewBenefitsByCityId(cityId, pageable);
//		return getPageBenefitsPreviewsDto(benefits, "Новые бенефиты");
//	} else {
//		log.info("Поиск бенефитов в категории {} в городе {}", category.getName(), city.getName());
//		Page<Benefit> benefits = benefitRepository.findBenefitsBySlug(category, cityId, pageable);
//		return getPageBenefitsPreviewsDto(benefits, category.getName());
//	}
//}

//	private PageBenefitsPreviewsDto getPageBenefitsPreviewsDto(Page<Benefit> benefits, String categoryTitle) {
//		return PageBenefitsPreviewsDto.builder()
//				.totalPages(benefits.getTotalPages())
//				.totalElements(benefits.getTotalElements())
//				.categoryTitle(categoryTitle)
//				.benefits(benefits.stream()
//						.map(BENEFIT_MAPPER::toBenefitPreviewResponseDto).toList())
//				.build();
//	}

	@Override
	@Transactional
	public CardResponse changeCardStatus(UUID id, StatusCard statusCard) {

		Card card = cardRepository.findById(id)
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "Card not found"));

		card.setStatusCard(statusCard);
		cardRepository.save(card);

		return CARD_MAPPER.toCardResponse(card);
	}

	@Override
	public PageCardsResponse findMyCars(Integer page, Integer limit) {

		String usernameAuth = SecurityContextHolder.getContext().getAuthentication().getName();

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
