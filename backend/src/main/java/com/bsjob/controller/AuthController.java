package com.bsjob.controller;

import com.bsjob.common.ApiResponse;
import com.bsjob.dto.AuthRequest;
import com.bsjob.dto.LoginRequest;
import com.bsjob.entity.User;
import com.bsjob.service.UserService;
import com.bsjob.util.JwtUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@Validated @RequestBody AuthRequest request) {
        return ApiResponse.success(userService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@Validated @RequestBody LoginRequest request) {
        String token = userService.login(request);
        User user = userService.findById(jwtUtil.getUserId(token));
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        data.put("role", user.getRole());
        return ApiResponse.success(data);
    }
}
