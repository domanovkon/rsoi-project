package com.domanov.museumservice.service;

import com.domanov.museumservice.dto.MuseumsResponse;
import com.domanov.museumservice.model.Museum;
import com.domanov.museumservice.repository.MuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("MuseumService")
public class MuseumService {

    @Autowired
    MuseumRepository museumRepository;

    public List<MuseumsResponse> getAllMuseums() {
        List<Museum> museumList = museumRepository.findAll();
        List<MuseumsResponse> museumsResponseList = new ArrayList<>();
        MuseumsResponse museumsResponse = new MuseumsResponse();
        for (Museum museum : museumList) {
            museumsResponse.setName(museum.getName());
            museumsResponse.setCity(museum.getCity());
            museumsResponse.setCountry(museum.getCountry());
            museumsResponse.setDescription(museum.getDescription());
            museumsResponseList.add(museumsResponse);
        }
        return museumsResponseList;
    }
}
