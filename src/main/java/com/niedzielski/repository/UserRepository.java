package com.niedzielski.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niedzielski.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findOneByEmail(String email);

	public User findOneByUsername(String username);

	public User findOneByUsernameOrEmail(String email, String username);

}
