package com.microfit.microfit.controller;

import com.microfit.microfit.model.User;
import com.microfit.microfit.model.Workout;
import com.microfit.microfit.security.SecurityUtils;
import com.microfit.microfit.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Registration lives in AuthController (/api/auth/register) since it
    // returns a JWT. This resource is for managing already-authenticated users.

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                            @Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/workouts")
    public ResponseEntity<Workout> addWorkoutToUser(@PathVariable Long userId,
                                                      @Valid @RequestBody Workout workout) {
        Workout saved = userService.addWorkoutToUser(userId, workout);
        return ResponseEntity.ok(saved);
    }
}
