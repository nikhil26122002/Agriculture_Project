package com.itvedant.agriculture;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class Signup_Controller {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUp user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use!");
        }
        
        if (user.getRole() == null) {
            return ResponseEntity.badRequest().body("Role selection is required!");
        }
        
        return ResponseEntity.ok(userService.registerUser(user));
        
    }
    
    @PostMapping("/admin/signup")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignUp admin) {
        if (userService.findByEmail(admin.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use!");
        }
        return ResponseEntity.ok(userService.registerAdmin(admin));
    }
    
    @GetMapping
    public List<SignUp> getUsers() {
        return userService.showAll();
    }
}
