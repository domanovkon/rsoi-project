package com.domanov.vaadin.view;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "contact", layout = MainLayout.class)
@PageTitle("Контакты")
public class ContactView extends VerticalLayout {

    public ContactView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(new H3("Наши контакты"));
        Image contactImage = new Image("https://cdn-icons-png.flaticon.com/512/3135/3135822.png", "Адрес");
        Image vk = new Image("https://cdn-icons-png.flaticon.com/512/145/145813.png", "vk");
        Image phone = new Image("https://cdn-icons-png.flaticon.com/512/724/724664.png", "phone");
        contactImage.setHeight("80px");
        contactImage.setHeight("80px");
        vk.setHeight("50px");
        vk.setHeight("50px");
        phone.setHeight("60px");
        phone.setWidth("60px");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(phone);
        H4 h4 = new H4("+79123456789");
        horizontalLayout.add(phone, h4);
        add(horizontalLayout);
    }
}
