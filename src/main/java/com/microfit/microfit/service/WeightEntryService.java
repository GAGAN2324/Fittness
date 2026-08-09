package com.microfit.microfit.service;

import com.microfit.microfit.model.WeightEntry;

import java.util.List;

public interface WeightEntryService {

    WeightEntry addWeight(WeightEntry weightEntry);

    List<WeightEntry> getAllWeights();

    List<WeightEntry> getWeightsByUserId(Long userId);
}
