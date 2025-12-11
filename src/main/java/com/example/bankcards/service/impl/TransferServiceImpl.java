package com.example.bankcards.service.impl;

import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.TransferEntity;
import com.example.bankcards.exception.MyException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.service.TransferService;
import com.example.bankcards.util.StaticHelperClass;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

	private final TransferRepository transferRepository;

	//TODO в будущем лучше продумать инжект сервисов, а не репозиториев
	private final CardRepository cardRepository;

	@Override
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public String transfer(TransferRequestDto transferRequestDto) {

		Card cardFrom = cardRepository.findByCardNumber(transferRequestDto.getCardFrom())
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "CardFrom not found"));

		if(!cardFrom.getOwner().getUsername().equals(StaticHelperClass.getAuthUserName())) {
			throw new MyException(HttpStatus.FORBIDDEN, "This card is not your");
		}

		Card cardTo = cardRepository.findByCardNumber(transferRequestDto.getCardTo())
				.orElseThrow(() -> new MyException(HttpStatus.NOT_FOUND, "CardTo not found"));

		if(cardFrom.getBalance().compareTo(transferRequestDto.getAmount()) < 0) {
			throw new MyException(HttpStatus.BAD_REQUEST, "Not enough money");
		}

		TransferEntity transfer = TransferEntity.builder()
				.cardFrom(transferRequestDto.getCardFrom())
				.cardTo(transferRequestDto.getCardTo())
				.amount(transferRequestDto.getAmount())
				.createdAt(LocalDateTime.now())
				.build();

		transferRepository.save(transfer);

		cardFrom.setBalance(cardFrom.getBalance().subtract(transferRequestDto.getAmount()));
		cardTo.setBalance(cardTo.getBalance().add(transferRequestDto.getAmount()));

		cardRepository.save(cardFrom);
		cardRepository.save(cardTo);

		return "Transfer successful";
	}
}
