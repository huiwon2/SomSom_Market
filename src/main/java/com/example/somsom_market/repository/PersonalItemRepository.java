package com.example.somsom_market.repository;

import com.example.somsom_market.domain.item.PersonalItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalItemRepository extends JpaRepository<PersonalItem, Long> {
    List<PersonalItem> findBySellerIdOrderByStartDateDesc(String sellerId);
    List<PersonalItem> findAllByOrderByStartDateDesc();
}

