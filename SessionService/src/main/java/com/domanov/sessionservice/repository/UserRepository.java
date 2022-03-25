package com.domanov.sessionservice.repository;

import com.domanov.sessionservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
