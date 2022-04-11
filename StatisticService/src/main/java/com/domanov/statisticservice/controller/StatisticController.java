package com.domanov.statisticservice.controller;

import com.domanov.statisticservice.dto.AddStatRequest;
import com.domanov.statisticservice.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://localhost:8082/api/v1")
@RequestMapping("api/v1")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PostMapping("/transfer")
    public ResponseEntity<Object> moneyTransfer(@RequestBody AddStatRequest addStatRequest) {
        return statisticService.moneyTransfer(addStatRequest);
    }

    @PostMapping("/user-reg")
    public ResponseEntity<Object> addUserRegistrationStat(@RequestHeader("uid") String uid) {
        return statisticService.addUserRegistrationStat(uid);
    }

}
