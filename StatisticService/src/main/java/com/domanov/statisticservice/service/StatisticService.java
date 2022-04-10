package com.domanov.statisticservice.service;

import com.domanov.statisticservice.dto.AddStatRequest;
import com.domanov.statisticservice.model.MoneyTransfer;
import com.domanov.statisticservice.repository.MoneyTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("StatisticService")
public class StatisticService {

    @Autowired
    private MoneyTransferRepository moneyTransferRepository;

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
}
