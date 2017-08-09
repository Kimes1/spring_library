package com.niedzielski.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niedzielski.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
