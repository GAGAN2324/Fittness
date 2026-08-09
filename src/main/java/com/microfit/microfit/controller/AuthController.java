package com.microfit.microfit.controller;

import com.microfit.microfit.dto.AuthResponse;
import com.microfit.microfit.dto.LoginRequest;
import com.microfit.microfit.dto.RegisterRequest;
import com.microfit.microfit.model.User;
import com.microfit.microfit.security.JwtUtil;
import com.microfit.microfit.security.UserPrincipal;
import com.microfit.microfit.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // hashed inside UserService
        user.setAge(request.getAge());
        user.setHeight(request.getHeight());
        user.setWeight(request.getWeight());

        User saved = userService.registerUser(user);

        String token = jwtUtil.generateToken(saved.getEmail());

        AuthResponse response = new AuthResponse(
                token, saved.getId(), saved.getEmail(),
                saved.getFirstName() + " " + saved.getLastName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        // Throws BadCredentialsException on a wrong email/password, which
        // GlobalExceptionHandler turns into a clean 401 response.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();

        String token = jwtUtil.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse(
                token, user.getId(), user.getEmail(),
                user.getFirstName() + " " + user.getLastName());

        return ResponseEntity.ok(response);
    }
}
