package com.domanov.vaadin.view;

import com.domanov.vaadin.view.login.LoginView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
    }

    private void createHeader() {

        Button mainButton = new Button("Музеи России");
        mainButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Icon phoneIcon = new Icon("lumo", "phone");
        Button contactButton = new Button("Контакты", phoneIcon);
        contactButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Icon museumIcon = new Icon(VaadinIcon.INSTITUTION);
        Button museumButton = new Button("Купить билет", museumIcon);
        museumButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Icon userIcon = new Icon(VaadinIcon.USER);
        Button userButton = new Button("Личный кабинет", userIcon);
        userButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        RouterLink mainRouterLink = new RouterLink("", MainView.class);
        RouterLink contactRouterLink = new RouterLink("", ContactView.class);
        RouterLink museumRouterLink = new RouterLink("", MuseumView.class);
        RouterLink lkRouterLink = new RouterLink("", UserView.class);

        contactRouterLink.getElement().appendChild(contactButton.getElement());
        mainRouterLink.getElement().appendChild(mainButton.getElement());
        museumRouterLink.getElement().appendChild(museumButton.getElement());
        lkRouterLink.getElement().appendChild(userButton.getElement());
        lkRouterLink.getStyle().set("margin-left", "auto");
        lkRouterLink.getStyle().set("padding", "15px");
        addToNavbar(mainRouterLink);
        addToNavbar(museumRouterLink);
        addToNavbar(contactRouterLink);
        addToNavbar(lkRouterLink);
    }

}
