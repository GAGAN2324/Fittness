package com.microfit.microfit.controller;

import com.microfit.microfit.model.User;
import com.microfit.microfit.model.WeightEntry;
import com.microfit.microfit.security.SecurityUtils;
import com.microfit.microfit.service.WeightEntryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weights")
@CrossOrigin("*")
public class WeightEntryController {

    private final WeightEntryService weightEntryService;

    public WeightEntryController(WeightEntryService weightEntryService) {
        this.weightEntryService = weightEntryService;
    }

    @PostMapping
    public ResponseEntity<WeightEntry> addWeight(@Valid @RequestBody WeightEntry weightEntry) {

        // The logged-in user owns this entry, regardless of what the client sent.
        User currentUser = SecurityUtils.getCurrentUser();
        weightEntry.setUser(currentUser);

        WeightEntry saved = weightEntryService.addWeight(weightEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<WeightEntry>> getAllWeights() {
        return ResponseEntity.ok(weightEntryService.getAllWeights());
    }

    @GetMapping("/me")
    public ResponseEntity<List<WeightEntry>> getMyWeights() {
        User currentUser = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(weightEntryService.getWeightsByUserId(currentUser.getId()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WeightEntry>> getWeightsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(weightEntryService.getWeightsByUserId(userId));
    }
}
