package com.microfit.microfit.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BMIRequest {

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    private Double height;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;

    public BMIRequest() {
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
