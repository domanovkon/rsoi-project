package com.domanov.gatewayservice.controller;

import com.domanov.gatewayservice.dto.*;
import com.domanov.gatewayservice.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("https://localhost:8080/api/v1")
@RequestMapping("api/v1")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @GetMapping("/museums")
    @CrossOrigin(origins = "*")
    public ResponseEntity<MuseumPageResponse> getMuseums(@RequestHeader("jwt") String jwt, @RequestParam("page") int page, @RequestParam("size") int size) {
        return gatewayService.getMuseums(jwt, page, size);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser(@RequestHeader("jwt") String jwt) {
        return gatewayService.getUser(jwt);
    }

    @GetMapping("/museum")
    public ResponseEntity<MuseumInfoResponse> getMuseumInfo(@RequestHeader("jwt") String jwt, @RequestParam("uid") String uid) {
        return gatewayService.getMuseumInfo(jwt, uid);
    }

    @PostMapping("/tickets")
    public ResponseEntity<Object> buyTicket(@RequestHeader("jwt") String jwt, @RequestBody TicketBuyRequest ticketBuyRequest) {
        return gatewayService.buyTicket(jwt, ticketBuyRequest);
    }

    @GetMapping("/history")
    public ResponseEntity<TicketListDto> getTicketHistory(@RequestHeader("jwt") String jwt) {
        return gatewayService.getTicketHistory(jwt);
    }

    @PostMapping("/theme")
    public ResponseEntity<Object> changeTheme(@RequestHeader("jwt") String jwt,  @RequestHeader("theme") Boolean darkTheme) {
        return gatewayService.changeTheme(jwt, darkTheme);
    }

    @PostMapping("/user-reg")
    public ResponseEntity<Object> addUserRegistrationStat(@RequestHeader("jwt") String jwt) {
        return gatewayService.addUserRegistrationStat(jwt);
    }

    @GetMapping("/transfer")
    public ResponseEntity<List<MoneyTransferDto>> getMoneyTransfer(@RequestHeader("jwt") String jwt) {
        return gatewayService.getMoneyTransfer(jwt);
    }

    @GetMapping("/user-stat")
    public ResponseEntity<List<UserStatDto>> getUserStat(@RequestHeader("jwt") String jwt) {
        return gatewayService.getUserStat(jwt);
    }

    @GetMapping("/types")
    public ResponseEntity<List<MyseumTypeDto>> getMuseumTypes(@RequestHeader("jwt") String jwt) {
        return gatewayService.getMuseumTypes(jwt);
    }
}
