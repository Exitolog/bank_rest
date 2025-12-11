package com.example.bankcards.repository;

import com.example.bankcards.entity.CardRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRequestRepository extends JpaRepository<CardRequest, UUID> {

	Page<CardRequest> findAll(Pageable pageable);

}
