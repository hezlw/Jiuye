package com.bsjob.service;

import com.bsjob.dto.AuthRequest;
import com.bsjob.dto.LoginRequest;
import com.bsjob.entity.User;

public interface UserService {
    User register(AuthRequest request);

    String login(LoginRequest request);

    User findById(Long id);
}
