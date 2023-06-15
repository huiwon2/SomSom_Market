package com.example.somsom_market.repository;

import com.example.somsom_market.domain.GroupItem;
import com.example.somsom_market.domain.PersonalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GroupItemRepository extends JpaRepository<GroupItem, Long> {
    List<GroupItem> findBySellerIdOrderByStartDate(String sellerId); //사용자ID로 사용자의 그룹아이템 리스트 보여주기
    //List<GroupItem> findBySellerId(String sellerId);
}
