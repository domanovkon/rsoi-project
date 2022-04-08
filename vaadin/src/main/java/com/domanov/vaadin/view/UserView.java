package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.UserResponse;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
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
            Avatar avatar = new Avatar(userResponse.getName() + " " + userResponse.getSurname());
            avatar.setWidth("200px");
            avatar.setHeight("200px");
            H5 name = new H5(userResponse.getName() + "  " + userResponse.getSurname());
            H4 log = new H4(userResponse.getLogin());
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            String btnTxt = "Темная тема";
            if (themeList.contains(Lumo.DARK)) {
                btnTxt = "Светлая тема";
            }
            Button themeButton = new Button(btnTxt, e -> {
                if (themeList.contains(Lumo.DARK)) {
                    themeList.remove(Lumo.DARK);
                    e.getSource().setText("Темная тема");
                } else {
                    themeList.add(Lumo.DARK);
                    e.getSource().setText("Светлая тема");
                }
            });
            add(log);
            add(avatar);
            add(name);
            add(themeButton);
            add(new H3("История покупок"));
        } else {
            Notification.show("Что-то пошло не так");
        }

    }
}
