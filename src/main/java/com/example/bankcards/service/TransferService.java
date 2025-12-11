package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequestDto;

public interface TransferService {

	String transfer(TransferRequestDto transferRequestDto);
}
