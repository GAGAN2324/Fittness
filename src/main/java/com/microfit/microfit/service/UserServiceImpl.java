package com.microfit.microfit.service;

import com.microfit.microfit.exception.DuplicateEmailException;
import com.microfit.microfit.exception.ResourceNotFoundException;
import com.microfit.microfit.model.User;
import com.microfit.microfit.model.Workout;
import com.microfit.microfit.repository.UserRepository;
import com.microfit.microfit.repository.WorkoutRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                            WorkoutRepository workoutRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException(
                    "An account with email " + user.getEmail() + " already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {

        User existingUser = getUserById(id);

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setHeight(updatedUser.getHeight());
        existingUser.setWeight(updatedUser.getWeight());

        // Only touch the password if the caller actually supplied a new one.
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public Workout addWorkoutToUser(Long userId, Workout workout) {

        User user = getUserById(userId);
        workout.setUser(user);

        return workoutRepository.save(workout);
    }
}
