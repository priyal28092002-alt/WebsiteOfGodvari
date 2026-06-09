package com.godavari.user.controller;

import com.godavari.user.dto.RegisterRequest;
import com.godavari.user.model.User;
import com.godavari.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.info("==> GET USER | id={}", id);
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("==> GET ALL USERS");
        List<User> users = userService.getAllUsers();
        log.info("==> GET ALL USERS SUCCESS | count={}", users.size());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody RegisterRequest req) {
        log.info("==> UPDATE USER | id={}", id);
        try {
            User updated = userService.updateUser(id, req);
            log.info("==> UPDATE USER SUCCESS | id={}", id);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("==> UPDATE USER FAILED | id={}: {}", id, e.getMessage());
            throw e;
        }
    }
}
