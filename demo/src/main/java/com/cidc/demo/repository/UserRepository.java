
package com.cidc.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cidc.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);

	Optional<User> findById(int id);

	List<User> findByEmailIn(Set<String> keySet);

	Page<User> findAllByOrderByIdAsc(Pageable pageable);

	Page<User> findAllByOrderByIdDesc(Pageable pageable);

}
