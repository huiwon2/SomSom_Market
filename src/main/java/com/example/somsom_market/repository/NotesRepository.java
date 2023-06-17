package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Long> {
    /* 받은 쪽지 리스트 */
    List<Notes> findByToSellerIdOrderBySendedAtDesc(String toSellerId);

    /* 보낸 쪽지 리스트 */
    List<Notes> findByFromAccountIdOrderBySendedAtDesc(String fromAccountId);
}
