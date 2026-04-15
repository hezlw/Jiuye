package com.bsjob.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bsjob.dto.AuthRequest;
import com.bsjob.dto.LoginRequest;
import com.bsjob.entity.User;
import com.bsjob.mapper.UserMapper;
import com.bsjob.service.UserService;
import com.bsjob.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserMapper userMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User register(AuthRequest request) {
        User existing = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername()));
        if (existing != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (!("user".equals(request.getRole()) || "company".equals(request.getRole()))) {
            throw new RuntimeException("角色只能是 user 或 company");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());
        userMapper.insert(user);
        return user;
    }

    @Override
    public String login(LoginRequest request) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())
                .eq(User::getPassword, request.getPassword()));
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        return jwtUtil.generateToken(user.getId(), user.getRole());
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }
}
