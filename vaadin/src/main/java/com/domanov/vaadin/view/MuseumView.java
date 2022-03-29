package com.domanov.vaadin.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "museums", layout = MainLayout.class)
@PageTitle("Музеи")
public class MuseumView extends VerticalLayout {

    public MuseumView() {

    }
}
