package com.domanov.museumservice.service;

import com.domanov.museumservice.dto.*;
import com.domanov.museumservice.model.Museum;
import com.domanov.museumservice.model.Show;
import com.domanov.museumservice.repository.MuseumRepository;
import com.domanov.museumservice.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("MuseumService")
public class MuseumService {

    @Autowired
    private MuseumRepository museumRepository;

    @Autowired
    private ShowRepository showRepository;

    public MuseumPageResponse getMuseums (int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Museum> museumPage = museumRepository.findAll(pageable);
        MuseumPageResponse museumPageResponse = new MuseumPageResponse();
        museumPageResponse.setPage(page);
        museumPageResponse.setPageSize(museumPage.getNumberOfElements());
        museumPageResponse.setTotalElements((int) museumPage.getTotalElements());

        List<MuseumResponse>museumResponseList = new ArrayList<>();
        for (Museum museum : museumPage.getContent()) {
            MuseumResponse museumResponse = new MuseumResponse();
            museumResponse.setMuseum_uid(museum.getMuseum_uid());
            museumResponse.setName(museum.getName());
            museumResponse.setDescription(museum.getDescription());
            museumResponse.setType(museum.getMuseumType().getType());
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setCity(museum.getAddress().getCity());
            addressResponse.setHouse(museum.getAddress().getHouse());
            addressResponse.setStreet(museum.getAddress().getStreet());
            museumResponse.setAddress(addressResponse);
            museumResponse.setCity(museum.getAddress().getCity());
            museumResponseList.add(museumResponse);
        }
        museumPageResponse.setItems(museumResponseList);
        return museumPageResponse;
    }

    public MuseumInfoResponse getMuseumInfo (String uid) {
        Museum museum = museumRepository.findByUid(UUID.fromString(uid));
        List<Show> shows = showRepository.findAllShowInMuseum(museum);
        MuseumInfoResponse museumInfoResponse = new MuseumInfoResponse();
        museumInfoResponse.setMuseum_uid(museum.getMuseum_uid().toString());
        museumInfoResponse.setName(museum.getName());
        museumInfoResponse.setDescription(museum.getDescription());
        museumInfoResponse.setInn(museum.getInn());
        museumInfoResponse.setType(museum.getMuseumType().getType());
        museumInfoResponse.setEmail(museum.getEmail());
        museumInfoResponse.setLegalEntityName(museum.getLegalEntityName());
        museumInfoResponse.setOgrn(museum.getOgrn());
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setCity(museum.getAddress().getCity());
        addressResponse.setHouse(museum.getAddress().getHouse());
        addressResponse.setStreet(museum.getAddress().getStreet());
        museumInfoResponse.setAddress(addressResponse);
        List<ShowResponse> showResponses = new ArrayList<>();
        for (Show show : shows) {
            if (show.getEndDate() != null && show.getEndDate().isBefore(LocalDateTime.now())) {
                continue;
            }
            ShowResponse showResponse = new ShowResponse();
            showResponse.setName(show.getName());
            showResponse.setDescription(show.getDescription());
            showResponse.setShow_uid(show.getShow_uid().toString());
            showResponse.setEndDate(show.getEndDate());
            showResponse.setStartDate(show.getStartDate());
            showResponse.setPermanentExhibition(show.getPermanentExhibition());
            showResponse.setPrice(show.getPrice());
            showResponses.add(showResponse);
        }
        showResponses = showResponses.stream()
                .sorted(Comparator.comparing(ShowResponse::getPermanentExhibition))
                .collect(Collectors.toList());
        Collections.reverse(showResponses);
        museumInfoResponse.setShows(showResponses);
        return museumInfoResponse;
    }
}
