package com.domanov.statisticservice.controller;

import com.domanov.statisticservice.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("https://localhost:8082/api/v1")
@RequestMapping("api/v1")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;
}
