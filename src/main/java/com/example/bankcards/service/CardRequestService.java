package com.example.bankcards.service;

import com.example.bankcards.dto.CardRequestResponseDto;
import com.example.bankcards.dto.PageCardRequestResponseDto;

import java.util.UUID;

public interface CardRequestService {

	String createNewCardRequest();

	CardRequestResponseDto findCardRequestById(UUID id);

	PageCardRequestResponseDto findAllCardRequest(Integer page, Integer limit);
}
