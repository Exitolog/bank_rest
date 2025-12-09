package com.example.bankcards.service;

import com.example.bankcards.dto.UserCreateRequestDto;
import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
				.role(Role.USER)
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
	public List<UserResponseDto> findAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(USER_MAPPER::toUserResponseDTO)
				.toList();
	}
}
