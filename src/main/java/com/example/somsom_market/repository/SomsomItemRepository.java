package com.example.somsom_market.repository;

import com.example.somsom_market.domain.item.SomsomItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SomsomItemRepository extends JpaRepository<SomsomItem, Long> {
//    Optional<SomsomItem> findById(Long item_id);
    SomsomItem findItemById(Long item_id);
}
