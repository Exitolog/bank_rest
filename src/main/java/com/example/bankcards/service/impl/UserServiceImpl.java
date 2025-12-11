package com.example.bankcards.service.impl;

import com.example.bankcards.dto.PageUsersResponse;
import com.example.bankcards.dto.UserCreateRequestDto;
import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.entity.enums.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper USER_MAPPER;


	@Override
	public UserResponseDto saveUser(UserCreateRequestDto dto) {

		userRepository.findByUsername(dto.getUsername()).ifPresent(user -> {
			throw new RuntimeException("User already exists");
		});

		User newUser = User.builder()
				.username(dto.getUsername())
				.password(passwordEncoder.encode(dto.getPassword()))
				.role(dto.getRole() == null ? Role.USER : dto.getRole())
				.build();

		userRepository.save(newUser);

		return USER_MAPPER.toUserResponseDTO(newUser);
	}

	@Override
	public UserResponseDto findUserById(UUID id) {
		return USER_MAPPER.toUserResponseDTO(userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found")));
	}

	@Override
	public UserResponseDto findUserByUsername(String username) {
		return USER_MAPPER.toUserResponseDTO(userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found")));
	}

	@Override
	public PageUsersResponse findAllUsers(Integer page, Integer limit) {

		Pageable pageable = PageRequest.of(page - 1, limit);

		Page<User> userPage = userRepository.findAll(pageable);

		return PageUsersResponse.builder()
				.totalPages(userPage.getTotalPages())
				.totalElements(userPage.getTotalElements())
				.users(userPage.stream().map(USER_MAPPER::toUserResponseDTO).toList())
				.build();
	}
}
