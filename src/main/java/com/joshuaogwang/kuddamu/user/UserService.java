package com.joshuaogwang.kuddamu.user;

import com.joshuaogwang.kuddamu.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BlackListedTokenRepository blackListedTokenRepository;

    public UserResponse createUser(UserRequest userRequest) {
        var user = User
                .builder()
                .fullName(userRequest.getFullName())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .password(userRequest.getPassword())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return UserResponse
                .builder()
                .token(token)
                .user(user)
                .build();
    }

    public UserResponse loginUser(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        var user = userRepository.findByEmail(username).orElseThrow();
        var token = jwtService.generateToken(user);
        return UserResponse
                .builder()
                .token(token)
                .user(user)
                .build();
    }

    public String logoutUser(String tokenString) {
        String token = tokenString.substring(7);
        var blackListedToken = BlackListedToken
                .builder()
                .token(token)
                .date(new Date())
                .build();
        blackListedTokenRepository.save(blackListedToken);
        return "Successful.";
    }

    public List<User> fetchUserByRoles(String role){
        return userRepository.findByRole(role);
    }
}
