package com.domanov.museumservice.repository;

import com.domanov.museumservice.model.Museum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MuseumRepository extends JpaRepository<Museum, Integer> {

    @Query("SELECT f FROM Museum f WHERE f.museum_uid = ?1")
    Museum findByUid(UUID uid);

    @Query("SELECT f.name FROM Museum f WHERE f.museum_uid = ?1")
    String findNameByUid(UUID uid);
}
