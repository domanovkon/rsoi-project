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
        Image phone = new Image("phone.png", "phone");
        phone.setHeight("60px");
        phone.setWidth("60px");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(phone);
        H4 h4 = new H4("+79123456789");
        horizontalLayout.add(phone, h4);
        add(horizontalLayout);
    }
}
