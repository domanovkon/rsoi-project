package com.domanov.museumservice.repository;

import com.domanov.museumservice.model.Museum;
import com.domanov.museumservice.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    @Query("SELECT f FROM Show f WHERE f.museum = ?1")
    List<Show> findAllShowInMuseum(Museum museum);

    @Query("SELECT f.museum FROM Show f WHERE f.show_uid = ?1")
    Museum findByShowUid(UUID uid);
}
