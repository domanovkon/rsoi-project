package com.domanov.statisticservice.repository;

import com.domanov.statisticservice.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Integer> {

}
