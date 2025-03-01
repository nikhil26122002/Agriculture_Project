package com.itvedant.agriculture;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<SignUp, Long> {
    Optional<SignUp> findByEmail(String email);

	Object findByUsername(String username);
}


