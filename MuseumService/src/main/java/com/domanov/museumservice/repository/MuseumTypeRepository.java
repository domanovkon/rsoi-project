package com.domanov.museumservice.repository;

import com.domanov.museumservice.model.MuseumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MuseumTypeRepository extends JpaRepository<MuseumType, Integer> {

    @Query("SELECT f FROM MuseumType f WHERE f.type = ?1")
    MuseumType findByTypeName(String type);
}
