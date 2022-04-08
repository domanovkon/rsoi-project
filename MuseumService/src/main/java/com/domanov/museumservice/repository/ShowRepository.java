package com.domanov.museumservice.repository;

import com.domanov.museumservice.model.Museum;
import com.domanov.museumservice.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    @Query("SELECT f FROM Show f WHERE f.museum = ?1")
    List<Show> findAllShowInMuseum(Museum museum);
}
