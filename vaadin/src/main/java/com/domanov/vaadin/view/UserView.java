package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.*;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "user", layout = MainLayout.class)
@PageTitle("Личный кабинет")
public class UserView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    public UserView() {

    }

    @PostConstruct
    public void init() {
        setSizeFull();
        ResponseEntity<UserResponse> response = vaadinService.getUser();
        UserResponse userResponse = new UserResponse();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout profile = new VerticalLayout();
        VerticalLayout ticketHistory = new VerticalLayout();
        profile.setAlignItems(Alignment.CENTER);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            userResponse = response.getBody();
            Avatar avatar = new Avatar(userResponse.getName() + " " + userResponse.getSurname());
            avatar.setWidth("200px");
            avatar.setHeight("200px");
            H5 name = new H5(userResponse.getName() + "  " + userResponse.getSurname());
            name.getStyle().set("margin-top", "0px");
            H4 log = new H4(userResponse.getLogin());
            log.getStyle().set("margin-top", "0px");
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            String btnTxt = "Темная тема";
            if (themeList.contains(Lumo.DARK)) {
                btnTxt = "Светлая тема";
            }
            Button themeButton = new Button(btnTxt, e -> {
                if (themeList.contains(Lumo.DARK)) {
                    themeList.remove(Lumo.DARK);
                    e.getSource().setText("Темная тема");
                    ResponseEntity<Object> responseEntity = vaadinService.changeTheme(false);
                    if (!response.getStatusCode().equals(HttpStatus.OK)) {
                        Notification.show("Что-то пошло не так");
                    }
                } else {
                    themeList.add(Lumo.DARK);
                    e.getSource().setText("Светлая тема");
                    ResponseEntity<Object> responseEntity = vaadinService.changeTheme(true);
                    if (!response.getStatusCode().equals(HttpStatus.OK)) {
                        Notification.show("Что-то пошло не так");
                    }
                }
            });
            profile.add(avatar, log, name, themeButton);
            profile.getStyle().set("margin-left", "50px");

            if (userResponse.getRole().equals("USER")) {
                H3 historyTitle = new H3("История покупок");
                ticketHistory.getStyle().set("margin-top", "-32px");
                ticketHistory.add(historyTitle);
                ticketHistory.getStyle().set("margin-left", "90px");
                horizontalLayout.add(profile);
                ResponseEntity<TicketListDto> ticketHistoryResponse = vaadinService.getTicketHistory();
                if (ticketHistoryResponse.getStatusCode().equals(HttpStatus.OK)) {
                    if (ticketHistoryResponse.getBody().getTicketList().size() > 0) {
                        List<TicketDto> ticketList = ticketHistoryResponse.getBody().getTicketList().stream()
                                .sorted(Comparator.comparing(TicketDto::getDateTime)).collect(Collectors.toList());
                        Collections.reverse(ticketList);
                        Grid<TicketDto> grid = new Grid<>(TicketDto.class, false);
                        grid.addColumn(TicketDto::getMuseumName).setHeader("Музей")
                                .setFooter(String.format("Всего %s", ticketList.size())).setAutoWidth(true);
                        grid.addColumn(TicketDto::getShowName).setHeader("Выставка").setAutoWidth(true);
                        grid.addColumn(TicketDto::getPrice).setHeader("Цена").setAutoWidth(true);
                        grid.addColumn(TicketDto::getDate).setHeader("Дата").setWidth("7em").setTextAlign(ColumnTextAlign.END);
                        grid.setItems(ticketList);
                        grid.setMinWidth("860px");
                        grid.setHeight("450px");
                        grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
                        ticketHistory.add(grid);
                    } else {
                        ticketHistory.add(new Span("Купленных билетов нет"));
                    }
                    horizontalLayout.add(ticketHistory);
                }
            } else if (userResponse.getRole().equals("ADMIN")) {
                Button addMuseumButton = new Button("Добавить музей");
                profile.add(addMuseumButton);
                H3 transferTitle = new H3("Поступления на счет музеев");
                transferTitle.getStyle().set("margin-bottom", "-8px").set("margin-top", "14px");
                ticketHistory.getStyle().set("margin-top", "-32px");
                ticketHistory.add(transferTitle);
                ticketHistory.getStyle().set("margin-left", "90px");
                horizontalLayout.add(profile);
                ResponseEntity<List<MoneyTransferDto>> moneyTransferResponse = vaadinService.getMoneyTransfer();
                if (moneyTransferResponse.getStatusCode().equals(HttpStatus.OK) && moneyTransferResponse.getBody().size() > 0) {
                    List<MoneyTransferDto> moneyTransferDtoList = moneyTransferResponse.getBody().stream()
                            .sorted(Comparator.comparing(MoneyTransferDto::getDateTime)).collect(Collectors.toList());
                    Collections.reverse(moneyTransferDtoList);
                    Grid<MoneyTransferDto> grid = new Grid<>(MoneyTransferDto.class, false);
                    grid.addColumn(MoneyTransferDto::getMuseumName).setHeader("Музей")
                            .setFooter(String.format("Всего %s", moneyTransferDtoList.size())).setAutoWidth(true);
                    grid.addColumn(MoneyTransferDto::getAccrual).setHeader("Поступление").setAutoWidth(true);
                    grid.addColumn(MoneyTransferDto::getDate).setHeader("Дата").setWidth("7em").setTextAlign(ColumnTextAlign.END);
                    grid.setItems(moneyTransferDtoList);
                    grid.setMinWidth("860px");
                    grid.setMaxHeight("240px");
                    grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
                    ticketHistory.add(grid);

                    H3 userStatTitle = new H3("Регистрация новых пользователей");
                    userStatTitle.getStyle().set("margin-bottom", "-8px").set("margin-top", "5px");
                    ticketHistory.add(userStatTitle);
                    ResponseEntity<List<UserStatDto>> userStatResponse = vaadinService.getUserStat();
                    if (userStatResponse.getStatusCode().equals(HttpStatus.OK) && userStatResponse.getBody().size() > 0) {
                        List<UserStatDto> userStatDtoList = userStatResponse.getBody().stream()
                                .sorted(Comparator.comparing(UserStatDto::getRegisterDate)).collect(Collectors.toList());
                        Collections.reverse(userStatDtoList);
                        Grid<UserStatDto> gridUser = new Grid<>(UserStatDto.class, false);
                        gridUser.addColumn(UserStatDto::getName).setHeader("Имя")
                                .setFooter(String.format("Всего %s", userStatDtoList.size())).setAutoWidth(true);
                        gridUser.addColumn(UserStatDto::getUsername).setHeader("Логин").setAutoWidth(true);
                        gridUser.addColumn(UserStatDto::getStringRegisterDate).setHeader("Дата").setWidth("7em").setTextAlign(ColumnTextAlign.END);
                        gridUser.setItems(userStatDtoList);
                        gridUser.setMinWidth("860px");
                        gridUser.setMaxHeight("240px");
                        gridUser.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
                        ticketHistory.add(gridUser);
                    }
                } else {
                    Span err = new Span("Статистика в данный момент недоступна");
                    err.getStyle().set("width", "400px");
                    ticketHistory.add(err);
                }
                horizontalLayout.add(ticketHistory);
            }
            add(horizontalLayout);
        } else {
            Notification.show("Что-то пошло не так");
        }

    }
}
