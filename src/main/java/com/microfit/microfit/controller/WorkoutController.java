package com.microfit.microfit.controller;

import com.microfit.microfit.model.User;
import com.microfit.microfit.model.Workout;
import com.microfit.microfit.security.SecurityUtils;
import com.microfit.microfit.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin("*")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<Workout> addWorkout(@Valid @RequestBody Workout workout) {

        // The logged-in user owns this workout, regardless of what the client sent.
        User currentUser = SecurityUtils.getCurrentUser();
        workout.setUser(currentUser);

        Workout saved = workoutService.addWorkout(workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping("/me")
    public ResponseEntity<List<Workout>> getMyWorkouts() {
        User currentUser = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(workoutService.getWorkoutsByUserId(currentUser.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Workout>> getWorkoutsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id,
                                                   @Valid @RequestBody Workout workout) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, workout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}
