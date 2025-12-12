package com.example.bankcards.util;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.Role;
import com.example.bankcards.entity.enums.StatusCard;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.CardRequestRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TestDataInitializer implements ApplicationRunner {

	private final UserRepository userRepository;
	private final CardRepository cardRepository;
	private final CardRequestRepository cardRequestRepository;
	private final PasswordEncoder passwordEncoder;

	private static final String ADMIN_USERNAME = "admin";
	private static final String USER_USERNAME = "user";

	@Override
	public void run(ApplicationArguments args) {
		createAdminIfNotExists();
		createUserIfNotExists();
	}

	private void createAdminIfNotExists() {
		if (userRepository.findByUsername(ADMIN_USERNAME).isPresent()) {
			return;
		}

		User admin = User.builder()
				.username(ADMIN_USERNAME)
				.password(passwordEncoder.encode("admin"))
				.role(Role.ADMIN)
				.build();

		userRepository.save(admin);
		System.out.println("Администратор создан: username = " + ADMIN_USERNAME);


		Card card1 = createCard(admin, "5555555555551111", new BigDecimal("5000.00"), StatusCard.ACTIVE);
		Card card2 = createCard(admin, "5555555555552222", new BigDecimal("3000.50"), StatusCard.ACTIVE);
		cardRepository.save(card1);
		cardRepository.save(card2);
		System.out.println("2 карты созданы для администратора");

		CardRequest request = CardRequest.builder()
				.user(admin)
				.dateRequest(LocalDate.now().minusDays(2))
				.build();
		cardRequestRepository.save(request);
		System.out.println("Запрос на выпуск карты от администратора добавлен");
	}

	private void createUserIfNotExists() {
		if (userRepository.findByUsername(USER_USERNAME).isPresent()) {
			return;
		}

		User user = User.builder()
				.username(USER_USERNAME)
				.password(passwordEncoder.encode("user")) // Пароль: user123
				.role(Role.USER)
				.build();

		userRepository.save(user);
		System.out.println("Пользователь создан: username = " + USER_USERNAME);


		Card card1 = createCard(user, "4444444444441111", new BigDecimal("1000.00"), StatusCard.ACTIVE);
		Card card2 = createCard(user, "4444444444442222", new BigDecimal("250.75"), StatusCard.ACTIVE);
		cardRepository.save(card1);
		cardRepository.save(card2);
		System.out.println("2 карты созданы для пользователя");

		CardRequest request = CardRequest.builder()
				.user(user)
				.dateRequest(LocalDate.now().minusDays(1))
				.build();
		cardRequestRepository.save(request);
		System.out.println("Запрос на выпуск карты от пользователя добавлен");
	}

	private Card createCard(User owner, String number, BigDecimal balance, StatusCard status) {
		return Card.builder()
				.owner(owner)
				.cardNumber(number)
				.balance(balance)
				.statusCard(status)
				.expirationDate(LocalDate.now().plusYears(3))
				.build();
	}
}