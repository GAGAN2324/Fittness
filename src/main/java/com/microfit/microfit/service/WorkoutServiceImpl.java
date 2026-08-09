package com.microfit.microfit.service;

import com.microfit.microfit.exception.ResourceNotFoundException;
import com.microfit.microfit.model.Workout;
import com.microfit.microfit.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Workout addWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + id));
    }

    @Override
    public Workout updateWorkout(Long id, Workout workout) {

        Workout existingWorkout = getWorkoutById(id);

        existingWorkout.setExerciseName(workout.getExerciseName());
        existingWorkout.setDurationMinutes(workout.getDurationMinutes());
        existingWorkout.setCaloriesBurned(workout.getCaloriesBurned());
        existingWorkout.setWorkoutDate(workout.getWorkoutDate());

        return workoutRepository.save(existingWorkout);
    }

    @Override
    public void deleteWorkout(Long id) {
        Workout workout = getWorkoutById(id);
        workoutRepository.delete(workout);
    }

    @Override
    public List<Workout> getWorkoutsByUserId(Long userId) {
        return workoutRepository.findByUserId(userId);
    }
}
