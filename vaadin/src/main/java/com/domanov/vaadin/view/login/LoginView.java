package com.domanov.vaadin.view.login;

import com.domanov.vaadin.VaadinApplication;
import com.domanov.vaadin.dto.UserResponse;
import com.domanov.vaadin.service.VaadinService;
import com.domanov.vaadin.view.MainLayout;
import com.domanov.vaadin.view.register.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login", layout = MainLayout.class)
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
                    try {
                        Object user = vaadinService.authenticate(username.getValue(), password.getValue());
                        if (user instanceof UserResponse) {
                            UI.getCurrent().navigate("");
                        } else {
                            Notification.show("Неверный логин или пароль!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }),
                new RouterLink("Регистрация", RegisterView.class)
        );
    }

}
