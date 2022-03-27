package com.domanov.sessionservice.repository;

import com.domanov.sessionservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT f FROM User f WHERE f.login = ?1 AND f.password = ?2")
    User findByUsernameAndPassword(String username, String password);
}
