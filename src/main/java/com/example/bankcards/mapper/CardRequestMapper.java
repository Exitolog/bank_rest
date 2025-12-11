package com.example.bankcards.mapper;

import com.example.bankcards.dto.CardRequestResponseDto;
import com.example.bankcards.entity.CardRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CardRequestMapper {

	@Mapping(target = "userId", source = "user.id")
	CardRequestResponseDto toCardRequestResponseDto(CardRequest cardRequest);

}
