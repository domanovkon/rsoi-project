package com.domanov.museumservice.repository;

import com.domanov.museumservice.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Integer> {
}
