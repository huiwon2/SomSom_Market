package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Item;
import com.example.somsom_market.domain.SomsomItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SomsomItemRepository extends JpaRepository{
    SomsomItem findById(long item_id);
    SomsomItem findItemById(long item_id);
}
