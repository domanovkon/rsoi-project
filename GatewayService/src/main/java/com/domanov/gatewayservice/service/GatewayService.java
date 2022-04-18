package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


@Service("GatewayService")
public class GatewayService {

    @Autowired
    private MuseumClient museumClient;

    @Autowired
    private SessionClient sessionClient;

    @Autowired
    private TicketClient ticketClient;

    @Autowired
    private StatisticClient statisticClient;

    private static BlockingQueue<AddStatRequest> ticketStatQueue = new ArrayBlockingQueue<>(100);

    private static BlockingQueue<String> userStatQueue = new ArrayBlockingQueue<>(100);

    public ResponseEntity<MuseumPageResponse> getMuseums(String jwt, int page, int size) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity<>(museumClient.getMuseums(page, size), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<UserResponse> getUser(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity<>(sessionClient.getUser(validateToken.getLogin()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new UserResponse(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<MuseumInfoResponse> getMuseumInfo(String jwt, String uid) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity<>(museumClient.getMuseumInfo(uid), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> buyTicket(String jwt, TicketBuyRequest ticketBuyRequest) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                UserResponse userResponse = sessionClient.getUser(validateToken.getLogin());
                ResponseEntity<TicketResponse> ticketResponse = ticketClient.buyTicket(userResponse.getUser_uid().toString(), ticketBuyRequest);
                if (ticketResponse.getStatusCode().equals(HttpStatus.OK)) {
                    ResponseEntity<MuseumResponse> museumResponse = museumClient.buyTicket(ticketBuyRequest);
                    AddStatRequest addStatRequest = new AddStatRequest();
                    addStatRequest.setMuseum_uid(museumResponse.getBody().getMuseum_uid().toString());
                    addStatRequest.setPrice(ticketBuyRequest.getPrice());
                    addStatRequest.setAmount(ticketBuyRequest.getAmount());
                    addStatRequest.setTickets(ticketResponse.getBody().getTickets_uid());
                    try {
                        ResponseEntity<Object> statResponse = statisticClient.moneyTransfer(addStatRequest);
                    } catch (Exception e) {
                        ticketStatQueue.add(addStatRequest);
                        TicketStatConsumer ticketStatConsumer = new TicketStatConsumer(statisticClient);
                        ticketStatConsumer.start();
                        return new ResponseEntity<>(HttpStatus.OK);
                    }
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
                }
            } else {
                return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<TicketListDto> getTicketHistory(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                UserResponse userResponse = sessionClient.getUser(validateToken.getLogin());
                ResponseEntity<TicketListDto> ticketHistoryResponse = ticketClient.getTicketHistory(userResponse.getUser_uid().toString());
                if (ticketHistoryResponse.getStatusCode().equals(HttpStatus.OK) && ticketHistoryResponse.getBody().getTicketList() != null && ticketHistoryResponse.getBody().getTicketList().size() > 0) {
                    ResponseEntity<TicketListDto> ticketHistoryListResponse = museumClient.getShowMuseumList(ticketHistoryResponse.getBody());
                    return new ResponseEntity<>(ticketHistoryListResponse.getBody(), HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new TicketListDto(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new TicketListDto(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> changeTheme(String jwt, Boolean darkTheme) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                sessionClient.changeTheme(validateToken.getLogin(), darkTheme);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new TicketListDto(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> addUserRegistrationStat(String jwt) {
        ResponseEntity<UserResponse> user = this.getUser(jwt);
        if (user.getStatusCode().equals(HttpStatus.OK)) {
            try {
                return statisticClient.addUserRegistrationStat(user.getBody().getUser_uid().toString());
            } catch (Exception e) {
                userStatQueue.add(user.getBody().getUser_uid().toString());
                UserStatConsumer userStatConsumer = new UserStatConsumer(statisticClient);
                userStatConsumer.start();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity<List<MoneyTransferDto>> getMoneyTransfer(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                ResponseEntity<List<MoneyTransferDto>> response = statisticClient.getMoneyTransfer();
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().size() > 0) {
                    List<MoneyTransferDto> museumTransferList = museumClient.getMoneyTransfer(response.getBody());
                    for (MoneyTransferDto moneyTransferDto : museumTransferList) {
                        if (moneyTransferDto.getMuseumName() == null) {
                            moneyTransferDto.setMuseumName("[Музей удален]");
                        }
                    }
                    return new ResponseEntity<>(museumTransferList, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<UserStatDto>> getUserStat(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                ResponseEntity<List<UserStatDto>> response = statisticClient.getUserStat();
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().size() > 0) {
                    List<UserStatDto> userStatDtoList = sessionClient.getUserStat(response.getBody());
                    return new ResponseEntity<>(userStatDtoList, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<MyseumTypeDto>> getMuseumTypes(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity<>(museumClient.getMuseumTypes(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<MuseumInfoResponse> createMuseum(String jwt, MuseumInfoResponse museumInfoResponse) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return museumClient.createMuseum(museumInfoResponse);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> removeShow(String jwt, String uid) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return museumClient.removeShow(uid);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> removeMuseum(String jwt, String uid) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return museumClient.removeMuseum(uid);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<ShowResponse> createShow(String jwt, ShowResponse showResponse) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return museumClient.createShow(showResponse);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ShowResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<MuseumInfoResponse> changeMuseum(String jwt, MuseumInfoResponse museumInfoResponse) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return museumClient.changeMuseum(museumInfoResponse);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ValidateToken validate(String jwt) {
        try {
            return sessionClient.validate(jwt);
        } catch (Exception e) {
            return new ValidateToken();
        }
    }

    public ResponseEntity<AuthResponse> getTheme(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity<>(sessionClient.getTheme(validateToken.getLogin()), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Component
    public static class TicketStatConsumer extends Thread {

        private final StatisticClient statisticClient;

        @Autowired
        TicketStatConsumer(StatisticClient statisticClient) {
            this.statisticClient = statisticClient;
        }

        @Override
        public void run() {
            try {
                while (!GatewayService.ticketStatQueue.isEmpty()) {
                    Thread.sleep(10000);
                    AddStatRequest request = ticketStatQueue.take();
                    try {
                        statisticClient.moneyTransfer(request);
                    } catch (Exception e) {
                        ticketStatQueue.add(request);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Component
    public static class UserStatConsumer extends Thread {

        private final StatisticClient statisticClient;

        @Autowired
        UserStatConsumer(StatisticClient statisticClient) {
            this.statisticClient = statisticClient;
        }

        @Override
        public void run() {
            try {
                while (!GatewayService.userStatQueue.isEmpty()) {
                    Thread.sleep(10000);
                    String request = userStatQueue.take();
                    try {
                        statisticClient.addUserRegistrationStat(request);
                    } catch (Exception e) {
                        userStatQueue.add(request);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
