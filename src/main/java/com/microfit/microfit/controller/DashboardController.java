package com.microfit.microfit.controller;

import com.microfit.microfit.model.User;
import com.microfit.microfit.model.Workout;
import com.microfit.microfit.security.SecurityUtils;
import com.microfit.microfit.service.WorkoutService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final WorkoutService workoutService;

    public DashboardController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public Map<String, Object> getDashboard() {

        // Scoped to the logged-in user, not everyone's workouts mixed together.
        User currentUser = SecurityUtils.getCurrentUser();
        List<Workout> workouts = workoutService.getWorkoutsByUserId(currentUser.getId());

        int totalWorkouts = workouts.size();

        // Null-safe: caloriesBurned/durationMinutes were Integer, and unboxing a
        // null Integer straight into mapToInt used to throw an NPE.
        int totalCaloriesBurned = workouts.stream()
                .filter(w -> w.getCaloriesBurned() != null)
                .mapToInt(Workout::getCaloriesBurned)
                .sum();

        double averageWorkoutDuration = workouts.stream()
                .filter(w -> w.getDurationMinutes() != null)
                .mapToInt(Workout::getDurationMinutes)
                .average()
                .orElse(0);

        Map<String, Object> response = new HashMap<>();
        response.put("totalWorkouts", totalWorkouts);
        response.put("totalCaloriesBurned", totalCaloriesBurned);
        response.put("averageWorkoutDuration", averageWorkoutDuration);

        return response;
    }
}
