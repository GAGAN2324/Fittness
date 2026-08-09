package com.microfit.microfit.service;

import com.microfit.microfit.model.Workout;

import java.util.List;

public interface WorkoutService {

    Workout addWorkout(Workout workout);

    List<Workout> getAllWorkouts();

    Workout getWorkoutById(Long id);

    Workout updateWorkout(Long id, Workout workout);

    void deleteWorkout(Long id);

    List<Workout> getWorkoutsByUserId(Long userId);
}