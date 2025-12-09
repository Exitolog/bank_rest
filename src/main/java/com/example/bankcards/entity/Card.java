package com.example.bankcards.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@Schema(description = "Карта")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card implements Serializable {

	@Id
	@Schema(description = "Номер карты")
	private Long cardNumber;

	@Schema(description = "Владелец карты")
	private User owner;

	@Schema(description = "Дата окончания срока действия карты")
	private LocalDate expirationDate;

	@Schema(description = "Статус карты", example = "ACTIVE")
	private Status status;

	@Schema(description = "Баланс карты")
	private BigDecimal balance;

}
