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
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class Signin_admin {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signin")
	public ResponseEntity<?> loginAdmin(@RequestBody SignUp admin) {
	    SignUp existingAdmin = userService.findByEmail(admin.getEmail());
	    
	    if (existingAdmin == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin not registered. Please contact support.");
	    }

	    // Check if password matches (Assuming password is encrypted)
	    if (!passwordEncoder.matches(admin.getPassword(), existingAdmin.getPassword())) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid email or password.");
	    }

	    // Only allow ADMIN role to sign in
	    if (existingAdmin.getRole() != Role.ADMIN) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins are allowed to sign in!");
	    }

	    // Generate and return a response (Consider using JWT)
	    return ResponseEntity.ok(existingAdmin);
	}
}
