package com.example.bankcards.service;

import com.example.bankcards.dto.PageUsersResponse;
import com.example.bankcards.dto.UserCreateRequestDto;
import com.example.bankcards.dto.UserResponseDto;

import java.util.UUID;

public interface UserService {

	UserResponseDto saveUser(UserCreateRequestDto dto);

	UserResponseDto findUserById(UUID id);

	UserResponseDto findUserByUsername(String username);

	PageUsersResponse findAllUsers(Integer page, Integer limit);

}
