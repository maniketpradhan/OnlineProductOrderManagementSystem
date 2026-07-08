package com.maniket.oms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maniket.oms.dto.LoginRequest;
import com.maniket.oms.dto.LoginResponse;
import com.maniket.oms.dto.RegisterRequest;
import com.maniket.oms.entity.Role;
import com.maniket.oms.entity.User;
import com.maniket.oms.repository.RoleRepository;
import com.maniket.oms.repository.UserRepository;
import com.maniket.oms.service.AuthService;
import com.maniket.oms.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists!";
        }

        Role role = roleRepository.findByRoleName("CUSTOMER").orElse(null);

        if (role == null) {
            return "CUSTOMER role not found!";
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(role);

        userRepository.save(user);

        return "Registration Successful";
    }

    @Override
public LoginResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail()).orElse(null);

    if (user == null) {
        return new LoginResponse(null, null, null, null, null, "User not found");
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        return new LoginResponse(null, null, null, null, null, "Invalid Password");
    }

    String token = jwtService.generateToken(user.getEmail());

    return new LoginResponse(
            user.getUserId(),
            user.getFullName(),
            user.getEmail(),
            user.getRole().getRoleName(),
            token,
            "Login Successful");
}
    
    @Override
public String registerAdmin(RegisterRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {
        return "Email already exists!";
    }

    Role role = roleRepository.findByRoleName("ADMIN").orElse(null);

    if (role == null) {
        return "ADMIN role not found!";
    }

    User user = new User();
    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setPhone(request.getPhone());
    user.setRole(role);

    userRepository.save(user);

    return "Admin Registered Successfully";
}
}