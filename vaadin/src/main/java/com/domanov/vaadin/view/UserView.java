package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.TicketDto;
import com.domanov.vaadin.dto.TicketHistory;
import com.domanov.vaadin.dto.TicketListDto;
import com.domanov.vaadin.dto.UserResponse;
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
import java.time.format.DateTimeFormatter;
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
            add(horizontalLayout);
        } else {
            Notification.show("Что-то пошло не так");
        }

    }
}
