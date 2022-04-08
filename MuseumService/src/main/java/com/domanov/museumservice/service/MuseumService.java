package com.domanov.museumservice.service;

import com.domanov.museumservice.dto.AddressResponse;
import com.domanov.museumservice.dto.MuseumInfoResponse;
import com.domanov.museumservice.dto.MuseumPageResponse;
import com.domanov.museumservice.dto.MuseumResponse;
import com.domanov.museumservice.model.Museum;
import com.domanov.museumservice.repository.MuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("MuseumService")
public class MuseumService {

    @Autowired
    MuseumRepository museumRepository;

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
        return museumInfoResponse;
    }
}
