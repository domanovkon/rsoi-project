package com.domanov.museumservice.repository;

import com.domanov.museumservice.model.Museum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuseumRepository extends JpaRepository<Museum, Integer> {

}
