package com.domanov.museumservice.controller;

import com.domanov.museumservice.dto.MuseumInfoResponse;
import com.domanov.museumservice.dto.MuseumPageResponse;
import com.domanov.museumservice.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://localhost:8081/api/v1")
@RequestMapping("api/v1")
public class MuseumController {

    @Autowired
    private MuseumService museumService;

    @GetMapping("/museums")
    @CrossOrigin(origins = "*")
    public MuseumPageResponse getMuseums(@RequestParam("page") int page, @RequestParam("size") int size) {
        return museumService.getMuseums(page, size);
    }

    @GetMapping("/museum")
    @CrossOrigin(origins = "*")
    public MuseumInfoResponse getMuseumInfo(@RequestParam("uid") String uid) {
        return museumService.getMuseumInfo(uid);
    }
}
