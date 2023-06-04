package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Item;
import com.example.somsom_market.domain.SomsomItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SomsomItemRepository extends JpaRepository{
    Optional<SomsomItem> findById(long item_id);
    SomsomItem findItemById(long item_id);
}
