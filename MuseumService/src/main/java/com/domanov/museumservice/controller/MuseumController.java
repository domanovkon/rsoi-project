package com.domanov.museumservice.controller;

import com.domanov.museumservice.dto.MuseumsResponse;
import com.domanov.museumservice.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("https://localhost:8081/api/v1")
@RequestMapping("api/v1")
public class MuseumController {

    @Autowired
    MuseumService museumService;

    @GetMapping("/museums")
    @CrossOrigin(origins = "*")
    public List<MuseumsResponse> getAllMuseums() {
        return museumService.getAllMuseums();
    }
}
