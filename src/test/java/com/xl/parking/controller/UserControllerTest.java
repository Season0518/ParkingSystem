package com.xl.parking.controller;

import com.xl.parking.domain.User;
import com.xl.parking.mapper.UserMapper;
import com.xl.parking.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    @Mock
    private UserMapper userMapper; // Mock UserMapper

    @InjectMocks
    private UserServiceImpl userService; // Inject Mocks into UserServiceImpl

    @Test
    void testLogin_WithValidUser_ReturnsSuccessfulLoginResponse() {
        // Arrange
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");

        User existingUser = new User();
        existingUser.setUsername("testUser");
        existingUser.setPassword("password");

        when(userMapper.selectOne(any())).thenReturn(existingUser);

        // Act
        Object response = userService.login(user);

        // Assert
        assertNotNull(response);
        assertTrue(response instanceof Map);

        Map<String, Object> loginResponse = (Map<String, Object>) response;
        assertTrue((Boolean) loginResponse.get("state"));
        assertEquals("验证成功", loginResponse.get("msg"));
        // Add more assertions if needed
    }

    @Test
    void testLogin_WithInvalidUser_ReturnsFailureResponse() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        when(userMapper.selectOne(any())).thenReturn(null);

        // Act
        Object response = userService.login(user);

        // Assert
        assertNotNull(response);
        assertTrue(response instanceof Map);

        Map<String, Object> loginResponse = (Map<String, Object>) response;
        assertFalse((Boolean) loginResponse.get("state"));
        assertEquals("用户名不存在", loginResponse.get("msg"));
        // Add more assertions if needed
    }

    @Test
    void register() {
    }

    @Test
    void checkUsername() {
    }
}