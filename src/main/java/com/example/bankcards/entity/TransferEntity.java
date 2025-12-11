package com.example.bankcards.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transfers")
@Schema(description = "Сущность перевода средств между картами")
public class TransferEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Schema(description = "Идентификатор перевода")
	private UUID id;

	@Schema(description = "Номер карты отправителя")
	private String cardFrom;

	@Schema(description = "Номер карты получателя")
	private String cardTo;

	@Schema(description = "Сумма перевода")
	private BigDecimal amount;

}
