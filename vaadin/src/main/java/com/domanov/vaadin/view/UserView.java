package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.UserResponse;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.atmosphere.config.service.Post;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Objects;

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
        setAlignItems(Alignment.CENTER);
        ResponseEntity<UserResponse> response = vaadinService.getUser();
        UserResponse userResponse = new UserResponse();
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            userResponse = response.getBody();
            add(new H3("Мой Профиль"));
            Avatar avatar = new Avatar(userResponse.getName()+ " " + userResponse.getSurname());
            avatar.setWidth("200px");
            avatar.setHeight("200px");
            H5 name = new H5(userResponse.getName() + "  " + userResponse.getSurname());
            H5 log = new H5(userResponse.getLogin());
            add(avatar);
            add(name);
            add(log);
            add(new H3("История покупок"));
        } else {
            Notification.show("Что-то пошло не так");
        }

    }
}
