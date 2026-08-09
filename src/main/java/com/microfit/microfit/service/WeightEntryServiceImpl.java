package com.microfit.microfit.service;

import com.microfit.microfit.model.WeightEntry;
import com.microfit.microfit.repository.WeightEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightEntryServiceImpl implements WeightEntryService {

    private final WeightEntryRepository repository;

    public WeightEntryServiceImpl(WeightEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public WeightEntry addWeight(WeightEntry weightEntry) {
        return repository.save(weightEntry);
    }

    @Override
    public List<WeightEntry> getAllWeights() {
        return repository.findAll();
    }

    @Override
    public List<WeightEntry> getWeightsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
