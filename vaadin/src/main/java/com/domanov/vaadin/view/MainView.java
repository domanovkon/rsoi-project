package com.domanov.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(new H1("Музеи России"));
        Image image = new Image("https://cdn-icons.flaticon.com/png/512/3200/premium/3200013.png?token=exp=1648232243~hmac=8aaa2d89f05e7e45ac7984412d1b1698", "art");
        image.setWidth("100px");
        image.setHeight("100px");
        add(image);
        add(new Button("Click me", e -> Notification.show("Фортинайти или бабаджу?")));
    }
}
