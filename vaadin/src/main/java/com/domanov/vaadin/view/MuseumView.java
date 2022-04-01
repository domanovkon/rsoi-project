package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.MuseumPageResponse;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;

@Route(value = "museums", layout = MainLayout.class)
@PageTitle("Музеи")
public class MuseumView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    public MuseumView() {
    }

    @PostConstruct
    public void init() {
        try {
            ResponseEntity<MuseumPageResponse> museums = vaadinService.getMuseums();
        } catch (Exception e) {
            Notification.show("Что-то пошло не так");
        }
    }

}
