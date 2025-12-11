package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

	Optional<Card> findByCardNumber(String cardNumber);

	boolean existsByCardNumber(String cardNumber);

	@Query("""
			SELECT c FROM Card c
			LEFT JOIN c.owner o
			WHERE(o.username = :username)
			""")
	@EntityGraph(attributePaths = {"owner"})
	Page<Card> findCardsByUsername(
			@Param("username") String username,
			Pageable pageable);

}
