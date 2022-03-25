package com.domanov.museumservice.repository;

import com.domanov.museumservice.model.MuseumType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuseumTypeRepository extends JpaRepository<MuseumType, Integer> {
}
