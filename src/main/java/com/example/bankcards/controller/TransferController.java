package com.example.bankcards.controller;

import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Schema(description = "Контроллер для работы с переводами")
@RequestMapping("/transfers")
public class TransferController {

	private final TransferService transferService;

	@Operation(summary = "Создание перевода.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Перевод успешно выполнен",
					content = @Content(schema = @Schema(implementation = String.class)))})
	@PostMapping("/new")
	public String transfer(@Valid @RequestBody TransferRequestDto transferRequestDto) {
		return transferService.transfer(transferRequestDto);
	}

}
