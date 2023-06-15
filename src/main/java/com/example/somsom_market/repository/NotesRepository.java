package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long> {
}
