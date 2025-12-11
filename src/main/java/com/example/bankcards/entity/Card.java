package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.StatusCard;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
	@GeneratedValue(strategy = GenerationType.UUID)
	@Schema(description = "Идентификатор карты")
	private UUID id;

	@Schema(description = "Номер карты")
	private String cardNumber;

	@Schema(description = "Дата окончания срока действия карты")
	private LocalDate expirationDate;

	@Schema(description = "Статус карты", example = "ACTIVE")
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusCard statusCard;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User owner;

	@Schema(description = "Баланс карты")
	private BigDecimal balance;

//	@Transient
//	public String getMaskedCardNumber() {
//		if (cardNumber == null || cardNumber.isEmpty())
//			return "**** **** **** ****";
//		try {
//			String decrypted = cardService.decrypt(cardNumber);
//			return "**** **** **** " + decrypted.substring(decrypted.length() - 4);
//		} catch (Exception e) {
//			return "**** **** **** ****";
//		}
//	}
}
