package com.microfit.microfit.controller;

import com.microfit.microfit.model.BMIRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bmi")
@CrossOrigin("*")
public class BMIController {

    @PostMapping("/calculate")
    public Map<String, Object> calculateBMI(@Valid @RequestBody BMIRequest request) {

        double heightInMeters = request.getHeight() / 100;

        double bmi = request.getWeight() / (heightInMeters * heightInMeters);

        String category;

        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi < 25) {
            category = "Normal Weight";
        } else if (bmi < 30) {
            category = "Overweight";
        } else {
            category = "Obese";
        }

        Map<String, Object> response = new HashMap<>();
        response.put("bmi", Math.round(bmi * 100.0) / 100.0);
        response.put("category", category);

        return response;
    }
}
