package com.domanov.statisticservice.service;

import com.domanov.statisticservice.dto.AddStatRequest;
import com.domanov.statisticservice.dto.MoneyTransferDto;
import com.domanov.statisticservice.dto.UserStatDto;
import com.domanov.statisticservice.model.MoneyTransfer;
import com.domanov.statisticservice.model.UserRegistration;
import com.domanov.statisticservice.repository.MoneyTransferRepository;
import com.domanov.statisticservice.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service("StatisticService")
public class StatisticService {

    @Autowired
    private MoneyTransferRepository moneyTransferRepository;

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    public ResponseEntity<Object> moneyTransfer(AddStatRequest addStatRequest) {
        List<MoneyTransfer> transferList = new ArrayList<>();
        for (String ticket : addStatRequest.getTickets()) {
            MoneyTransfer moneyTransfer = new MoneyTransfer();
            moneyTransfer.setTicket_uid(UUID.fromString(ticket));
            moneyTransfer.setAccrual(addStatRequest.getPrice());
            moneyTransfer.setMuseum_uid(UUID.fromString(addStatRequest.getMuseum_uid()));
            moneyTransfer.setDateOfTransfer(LocalDateTime.now());
            transferList.add(moneyTransfer);
        }
        for (MoneyTransfer moneyTransfer : transferList) {
            moneyTransferRepository.save(moneyTransfer);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> addUserRegistrationStat(String uid) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setUser_uid(UUID.fromString(uid));
        userRegistration.setDateOfRegistration(LocalDateTime.now());
        userRegistrationRepository.save(userRegistration);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<MoneyTransferDto>> getMoneyTransfer() {
        List<MoneyTransfer> moneyTransferList = moneyTransferRepository.findAll();
        List<MoneyTransferDto> moneyTransferDtoList = new ArrayList<>();
        for (MoneyTransfer moneyTransfer : moneyTransferList) {
            MoneyTransferDto moneyTransferDto = new MoneyTransferDto();
            moneyTransferDto.setTicket_uid(moneyTransfer.getTicket_uid().toString());
            moneyTransferDto.setMuseum_uid(moneyTransfer.getMuseum_uid().toString());
            moneyTransferDto.setDateTime(moneyTransfer.getDateOfTransfer());
            moneyTransferDto.setAccrual(moneyTransfer.getAccrual());
            String date = DateTimeFormatter.ofPattern("d MMMM yyyy")
                    .withLocale(new Locale("ru"))
                    .format((moneyTransfer.getDateOfTransfer()));
            moneyTransferDto.setDate(date);
            moneyTransferDtoList.add(moneyTransferDto);
        }
        return new ResponseEntity<>(moneyTransferDtoList, HttpStatus.OK);
    }

    public ResponseEntity<List<UserStatDto>> getUserStat() {
        List<UserRegistration> userRegistrationsList = userRegistrationRepository.findAll();
        List<UserStatDto> userStatDtoList = new ArrayList<>();
        for (UserRegistration userRegistration : userRegistrationsList) {
            UserStatDto userStatDto = new UserStatDto();
            userStatDto.setUser_uid(userRegistration.getUser_uid().toString());
            userStatDto.setRegisterDate(userRegistration.getDateOfRegistration());
            String date = DateTimeFormatter.ofPattern("d MMMM yyyy")
                    .withLocale(new Locale("ru"))
                    .format((userRegistration.getDateOfRegistration()));
            userStatDto.setStringRegisterDate(date);
            userStatDtoList.add(userStatDto);
        }
        return new ResponseEntity<>(userStatDtoList, HttpStatus.OK);
    }
}
