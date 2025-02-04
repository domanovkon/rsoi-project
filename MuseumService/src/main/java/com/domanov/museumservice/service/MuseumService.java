package com.domanov.museumservice.service;

import com.domanov.museumservice.dto.*;
import com.domanov.museumservice.model.Address;
import com.domanov.museumservice.model.Museum;
import com.domanov.museumservice.model.MuseumType;
import com.domanov.museumservice.model.Show;
import com.domanov.museumservice.repository.MuseumRepository;
import com.domanov.museumservice.repository.MuseumTypeRepository;
import com.domanov.museumservice.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private MuseumTypeRepository museumTypeRepository;

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

    public ResponseEntity<MuseumResponse> buyTicket(TicketBuyRequest ticketBuyRequest) {
        Museum museum = showRepository.findByShowUid(UUID.fromString(ticketBuyRequest.getShow_uid()));
        Integer account = museum.getAccount();
        museum.setAccount(account + ticketBuyRequest.getAmount() * ticketBuyRequest.getPrice());
        museumRepository.save(museum);
        MuseumResponse museumResponse = new MuseumResponse();
        museumResponse.setMuseum_uid(museum.getMuseum_uid());
        return new ResponseEntity<>(museumResponse, HttpStatus.OK);
    }

    public ResponseEntity<TicketListDto> getShowMuseumList(TicketListDto ticketListDto) {
        List<TicketDto> ticketList = new ArrayList<>();
        for (TicketDto ticketDto : ticketListDto.getTicketList()) {
            TicketDto ticketDto1 = new TicketDto();
            Show show = showRepository.findShowByShowUid(UUID.fromString(ticketDto.getShow_uid()));
            if (show == null) {
                continue;
            }
            ticketDto1.setShowName(show.getName());
            ticketDto1.setMuseumName(show.getMuseum().getName());
            ticketDto1.setPrice(ticketDto.getPrice());
            ticketDto1.setDate(ticketDto.getDate());
            ticketDto1.setDateTime(ticketDto.getDateTime());
            ticketList.add(ticketDto1);
        }
        TicketListDto ticketListDto1 = new TicketListDto();
        ticketListDto1.setTicketList(ticketList);
        return new ResponseEntity<>(ticketListDto1, HttpStatus.OK);
    }

    public List<MoneyTransferDto> getMoneyTransfer(List<MoneyTransferDto> moneyTransferDtoList) {
        List<MoneyTransferDto> moneyTransferDtos = new ArrayList<>();
        for (MoneyTransferDto moneyTransferDto : moneyTransferDtoList) {
            moneyTransferDto.setMuseumName(museumRepository.findNameByUid(UUID.fromString(moneyTransferDto.getMuseum_uid())));
            moneyTransferDtos.add(moneyTransferDto);
        }
        return moneyTransferDtos;
    }

    public List<MyseumTypeDto> getMuseumTypes() {
        List<MyseumTypeDto> myseumTypeDtos = new ArrayList<>();
        List<MuseumType> museumTypes = museumTypeRepository.findAll();
        for (MuseumType museumType : museumTypes) {
            MyseumTypeDto myseumTypeDto = new MyseumTypeDto();
            myseumTypeDto.setId(museumType.getId());
            myseumTypeDto.setType(museumType.getType());
            myseumTypeDtos.add(myseumTypeDto);
        }
        myseumTypeDtos = myseumTypeDtos.stream()
                .sorted(Comparator.comparing(MyseumTypeDto::getId))
                .collect(Collectors.toList());
        return myseumTypeDtos;
    }

    public ResponseEntity<MuseumInfoResponse> createMuseum(MuseumInfoResponse museumInfoResponse) {
        Address address = new Address();
        address.setCity(museumInfoResponse.getAddress().getCity());
        address.setStreet(museumInfoResponse.getAddress().getStreet());
        address.setHouse(museumInfoResponse.getAddress().getHouse());
        Museum museum = new Museum();
        museum.setName(museumInfoResponse.getName());
        museum.setDescription(museumInfoResponse.getDescription());
        museum.setEmail(museumInfoResponse.getEmail());
        museum.setAddress(address);
        MuseumType museumType = museumTypeRepository.findByTypeName(museumInfoResponse.getType());
        museum.setMuseumType(museumType);
        museum.setInn(museumInfoResponse.getInn());
        museum.setOgrn(museumInfoResponse.getOgrn());
        UUID museum_uid = UUID.randomUUID();
        museum.setMuseum_uid(museum_uid);
        museum.setLegalEntityName(museumInfoResponse.getLegalEntityName());
        museum.setAccount(0);
        museumRepository.save(museum);
        museumInfoResponse.setMuseum_uid(museum_uid.toString());
        return new ResponseEntity<>(museumInfoResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> removeShow(String uid) {
        Show show = showRepository.findShowByShowUid(UUID.fromString(uid));
        showRepository.delete(show);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> removeMuseum(String uid) {
        Museum museum = museumRepository.findByUid(UUID.fromString(uid));
        List<Show> shows = showRepository.findAllShowInMuseum(museum);
        for (Show show : shows) {
            showRepository.delete(show);
        }
        museumRepository.delete(museum);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<ShowResponse> createShow(ShowResponse showResponse) {
        Show show = new Show();
        show.setShow_uid(UUID.randomUUID());
        show.setName(showResponse.getName());
        show.setDescription(showResponse.getDescription());
        show.setPrice(showResponse.getPrice());
        show.setPermanentExhibition(showResponse.getPermanentExhibition());
        if (!showResponse.getPermanentExhibition()) {
            show.setStartDate(showResponse.getStartDate());
            show.setEndDate(showResponse.getEndDate());
        }
        Museum museum = museumRepository.findByUid(UUID.fromString(showResponse.getMuseum_uid()));
        show.setMuseum(museum);
        showRepository.save(show);
        return new ResponseEntity<>(showResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<MuseumInfoResponse> changeMuseum(MuseumInfoResponse museumInfoResponse) {
        Museum museum = museumRepository.findByUid(UUID.fromString(museumInfoResponse.getMuseum_uid()));

        Address address = new Address();
        address.setCity(museumInfoResponse.getAddress().getCity());
        address.setStreet(museumInfoResponse.getAddress().getStreet());
        address.setHouse(museumInfoResponse.getAddress().getHouse());
        museum.setAddress(address);
        museum.setName(museumInfoResponse.getName());
        museum.setDescription(museumInfoResponse.getDescription());
        museum.setEmail(museumInfoResponse.getEmail());

        MuseumType museumType = museumTypeRepository.findByTypeName(museumInfoResponse.getType());
        museum.setMuseumType(museumType);
        museum.setInn(museumInfoResponse.getInn());
        museum.setOgrn(museumInfoResponse.getOgrn());
        museum.setLegalEntityName(museumInfoResponse.getLegalEntityName());
        museumRepository.save(museum);
        return new ResponseEntity<>(museumInfoResponse, HttpStatus.OK);
    }
}
