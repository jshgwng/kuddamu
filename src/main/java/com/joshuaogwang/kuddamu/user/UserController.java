package com.joshuaogwang.kuddamu.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {
    final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestParam("username") String username, @RequestParam("password")String password){
        return ResponseEntity.ok(userService.loginUser(username,password));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.logoutUser(token));
    }
}
