package com.example.bankcards.mapper;

import com.example.bankcards.dto.BalanceCardResponseDto;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CardMapper {


	@Mapping(target = "ownerUsername", source = "owner.username")
	@Mapping(target = "status", source = "statusCard")
	CardResponse toCardResponse(Card card);

	BalanceCardResponseDto toBalanceCardResponseDto(Card card);

}
