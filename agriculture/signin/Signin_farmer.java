package com.itvedant.agriculture.signin;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itvedant.agriculture.Role;
import com.itvedant.agriculture.SignUp;
import com.itvedant.agriculture.UserService;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class Signin_farmer {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signin")
	public ResponseEntity<?> loginUser(@RequestBody SignUp user) {
	    SignUp existingUser = userService.findByEmail(user.getEmail());
	    
	    if (existingUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not registered. Please sign up first.");
	    }

	    // Check if password matches (Assuming password is encrypted)
	    if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid email or password.");
	    }

	    // Only allow FARMER role to sign in
	    if (existingUser.getRole() != Role.FARMER) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only farmers are allowed to sign in!");
	    }

	    // Generate and return a response (Consider using JWT)
	    return ResponseEntity.ok(existingUser);
	}

}