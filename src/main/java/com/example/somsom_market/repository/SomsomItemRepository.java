package com.example.somsom_market.repository;

import com.example.somsom_market.domain.item.SomsomItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SomsomItemRepository extends JpaRepository<SomsomItem, Long> {
    Optional<SomsomItem> findById(long item_id);
    SomsomItem findItemById(long item_id);
}
