package com.domanov.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Главная страница ")
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        Image mainImage = new Image("https://cdn-icons.flaticon.com/png/512/4624/premium/4624671.png?token=exp=1648402078~hmac=ac671a108f168f8486def09badde152d", "Матрешка)");
        mainImage.setHeight("250px");
        mainImage.setHeight("250px");
        add(mainImage);
        add(new H1("Музеи России"));
        add(new H4("Покупайте билеты в любой музей России!"));
        Image imageArt = new Image("https://cdn-icons.flaticon.com/png/512/3200/premium/3200013.png?token=exp=1648232243~hmac=8aaa2d89f05e7e45ac7984412d1b1698", "art");
        imageArt.setWidth("80px");
        imageArt.setHeight("80px");
        Image imageSculpture = new Image("https://cdn-icons-png.flaticon.com/512/6749/6749585.png", "sculpture");
        imageSculpture.setWidth("80px");
        imageSculpture.setHeight("80px");
        Image imageBook = new Image("https://cdn-icons.flaticon.com/png/512/3145/premium/3145765.png?token=exp=1648403838~hmac=065761d114f26f57c6f1282fca572b0c", "book");
        imageBook.setWidth("80px");
        imageBook.setHeight("80px");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(imageArt, imageBook, imageSculpture);
        add(horizontalLayout);
    }
}
