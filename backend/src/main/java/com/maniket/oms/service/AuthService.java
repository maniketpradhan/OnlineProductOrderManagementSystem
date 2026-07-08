package com.maniket.oms.service;

import com.maniket.oms.dto.LoginRequest;
import com.maniket.oms.dto.LoginResponse;
import com.maniket.oms.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);
    
    String registerAdmin(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}