package com.domanov.vaadin.view.register;

import com.domanov.vaadin.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "register", layout = MainLayout.class)
public class RegisterView extends VerticalLayout  {

    public RegisterView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        TextField username = new TextField("Логин");
        PasswordField password1 = new PasswordField("Пароль");
        PasswordField password2 = new PasswordField("Подтверждение пароля");
        add(
                new H2("Регистрация"),
                username,
                password1,
                password2,
                new Button("Зарегистрироваться", event -> register(
                        username.getValue(),
                        password1.getValue(),
                        password2.getValue()
                )));
    }

    private void register(String username, String password1, String password2) {
        if (username.trim().isEmpty()) {
            Notification.show("Введите логин");
        } else if (password1.isEmpty()) {
            Notification.show("Введите пароль");
        } else if (!password1.equals(password2)) {
            Notification.show("Пароли не совпадают");
        } else {
            Notification.show("123");
        }
    }
}
