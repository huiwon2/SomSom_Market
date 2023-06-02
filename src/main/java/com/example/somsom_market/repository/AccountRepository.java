package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByIdAndPassword(String id, String password);
    Optional<Account> findById(String id);
}
