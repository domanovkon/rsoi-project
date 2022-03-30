package com.domanov.vaadin.view.register;

import com.domanov.vaadin.service.VaadinService;
import com.domanov.vaadin.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "register", layout = MainLayout.class)
public class RegisterView extends VerticalLayout  {

    @Autowired
    private VaadinService vaadinService;

    public RegisterView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        TextField name = new TextField("Имя");
        TextField surname = new TextField("Фамилия");
        TextField username = new TextField("Логин");
        PasswordField password1 = new PasswordField("Пароль");
        PasswordField password2 = new PasswordField("Подтверждение пароля");
        add(
                new H2("Регистрация"),
                name,
                surname,
                username,
                password1,
                password2,
                new Button("Зарегистрироваться", event -> register(
                        name.getValue(),
                        surname.getValue(),
                        username.getValue(),
                        password1.getValue(),
                        password2.getValue()
                )));
    }

    private void register(String name, String surname, String username, String password1, String password2) {
        if (name.trim().isEmpty()) {
            Notification.show("Введите имя");
        } else if (surname.trim().isEmpty()) {
            Notification.show("Введите фамилию");
        } else if (username.trim().isEmpty()) {
            Notification.show("Введите логин");
        } else if (password1.isEmpty()) {
            Notification.show("Введите пароль");
        } else if (!password1.equals(password2)) {
            Notification.show("Пароли не совпадают");
        } else {
            try {
                Object a = vaadinService.registration(username, name, surname, password1);
                Notification.show(a.toString());
//                Notification.show("123");
            } catch (Exception e) {
                Notification.show("123");
            }
        }
    }
}
