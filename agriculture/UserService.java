package com.itvedant.agriculture;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SignUp registerUser(SignUp user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.FARMER);
        return userRepository.save(user);
        
        
    }
    
    public SignUp registerAdmin(SignUp admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(Role.ADMIN); // Ensuring role is set as ADMIN
        return userRepository.save(admin);
    }
    
    

    public SignUp findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    
    
    public List<SignUp> showAll() { 
		return userRepository.findAll();
	}
    
    public SignUp findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}
