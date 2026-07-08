package com.maniket.oms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.maniket.oms.dto.LoginRequest;
import com.maniket.oms.dto.LoginResponse;
import com.maniket.oms.dto.RegisterRequest;
import com.maniket.oms.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {

        return authService.register(request);

    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        return authService.login(request);

    }

    @PostMapping("/register-admin")
public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterRequest request) {

    return ResponseEntity.ok(authService.registerAdmin(request));

}

}