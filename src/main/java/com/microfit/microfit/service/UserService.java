package com.microfit.microfit.service;

import com.microfit.microfit.model.User;
import com.microfit.microfit.model.Workout;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    Workout addWorkoutToUser(Long userId, Workout workout);
}