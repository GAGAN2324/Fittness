package com.microfit.microfit.repository;

import com.microfit.microfit.model.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeightEntryRepository extends JpaRepository<WeightEntry, Long> {

    List<WeightEntry> findByUserId(Long userId);
}
