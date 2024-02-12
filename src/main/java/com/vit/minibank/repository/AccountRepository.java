package com.vit.minibank.repository;

import com.vit.minibank.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUuid(UUID accountNumber);

    Optional<Account> findByUuidAndCode(UUID accountNumber, String code);
}
