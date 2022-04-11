package com.domanov.vaadin.view.login;

import com.domanov.vaadin.dto.AuthResponse;
import com.domanov.vaadin.service.VaadinService;
import com.domanov.vaadin.view.register.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Route(value = "authorization")
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    public LoginView() {
        setId("login-view");
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        var username = new TextField("Логин");
        username.setMaxLength(80);
        var password = new PasswordField("Пароль");
        password.setMaxLength(80);
        add(
                new H2("Добро пожаловать!"),
                username,
                password,
                new Button("Войти", event -> {
                    if (username.getValue().isEmpty()) {
                        Notification.show("Введите логин");
                    } else if (password.getValue().isEmpty()) {
                        Notification.show("Введите пароль");
                    } else {
                        ResponseEntity<AuthResponse> response = vaadinService.authenticate(username.getValue(), password.getValue());
                        if (response.getStatusCode().equals(HttpStatus.OK)) {
                            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
                            if (response.getBody().getDarkTheme()) {
                                themeList.add(Lumo.DARK);
                            } else {
                                themeList.remove(Lumo.DARK);
                            }
                            UI.getCurrent().navigate("");
                        } else if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                            Notification.show("Неверный логин или пароль!");
                        } else {
                            Notification.show("Сервис недоступен, попробуйте позже");
                        }
                    }
                }),
                new RouterLink("Регистрация", RegisterView.class)
        );
    }

}
