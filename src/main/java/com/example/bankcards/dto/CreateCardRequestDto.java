package com.example.bankcards.dto;

import java.util.UUID;

public record CreateCardRequestDto(
		UUID userId) {
}
