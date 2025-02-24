package com.movierecommendation.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movierecommendation.dto.AuthRequest;
import com.movierecommendation.dto.AuthResponse;
import com.movierecommendation.models.User;
import com.movierecommendation.repository.UserRepository;
import com.movierecommendation.security.JwtUtil;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String signUp(AuthRequest request){
        Optional<User> exisitingUser=userRepository.findByEmail(request.getEmail());
        if(exisitingUser.isPresent()){
            throw new RuntimeException("Email already exisit");
        }
        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "User registered successfully.";
        
    }
    public AuthResponse login(AuthRequest request){
        User user=userRepository.findByEmail(request.getEmail())
                        .orElseThrow(()-> new RuntimeException("Invalid Credentials"));
        
        boolean isValidPassword=passwordEncoder.matches(request.getPassword(),user.getPassword());
        if(!isValidPassword){
            throw new RuntimeException("Invalid Credentials");
        }
        String token=jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);

    }
    
    

    
}
