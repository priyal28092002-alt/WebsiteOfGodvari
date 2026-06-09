package com.godavari.user.service;

import com.godavari.user.dto.*;
import com.godavari.user.exception.ResourceNotFoundException;
import com.godavari.user.model.User;
import com.godavari.user.repository.UserRepository;
import com.godavari.user.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already registered");

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setGstNumber(req.getGstNumber());
        user.setCompanyName(req.getCompanyName());
        user.setAddress(req.getAddress());
        user.setCity(req.getCity());
        user.setState(req.getState());
        user.setPincode(req.getPincode());

        if (req.getRole() != null) {
            try { user.setRole(User.Role.valueOf(req.getRole().toUpperCase())); }
            catch (IllegalArgumentException e) { user.setRole(User.Role.CUSTOMER); }
        }

        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole().name(), saved.getId());
        return new AuthResponse(token, saved.getEmail(), saved.getName(), saved.getRole().name(), saved.getId(), saved.getPhone());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid email or password");
        if (!user.isActive())
            throw new IllegalArgumentException("Account is deactivated");
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        return new AuthResponse(token, user.getEmail(), user.getName(), user.getRole().name(), user.getId(), user.getPhone());
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User updateUser(Long id, RegisterRequest req) {
        User user = getById(id);
        if (req.getName() != null) user.setName(req.getName());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getAddress() != null) user.setAddress(req.getAddress());
        if (req.getCity() != null) user.setCity(req.getCity());
        if (req.getState() != null) user.setState(req.getState());
        if (req.getPincode() != null) user.setPincode(req.getPincode());
        if (req.getGstNumber() != null) user.setGstNumber(req.getGstNumber());
        if (req.getCompanyName() != null) user.setCompanyName(req.getCompanyName());
        return userRepository.save(user);
    }
}
