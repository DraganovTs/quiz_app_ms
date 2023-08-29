package com.homecode.controller;

import com.homecode.model.dto.UserDTO;
import com.homecode.model.dto.UserRegisterDTO;
import com.homecode.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> save(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(userService.register(userRegisterDTO));
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("Hello, from secured endpoint!");
    }
}
