package com.cidc.demo;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);

	Optional<User> findById(int id);

	int findAllById(int id);
	
	User findEmail(String email);

}
