package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Schema(description = "Идентификатор пользователя")
	private UUID id;

	@Schema(description = "Информация о пользователе")
	private String username;

	@Schema(description = "Пароль пользователя")
	private String password;

	@Enumerated(EnumType.STRING)
	@Schema(description = "Роль пользователя")
	private Role role;

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Schema(description = "Карты пользователя")
	Set<Card> cards = new HashSet<>();
}