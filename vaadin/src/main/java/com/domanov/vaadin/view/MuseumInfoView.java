package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.MuseumInfoResponse;
import com.domanov.vaadin.dto.ShowResponse;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Route(value = "museum", layout = MainLayout.class)
@PageTitle("Музей")
public class MuseumInfoView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private VaadinService vaadinService;

    private String museum_uid;

    public MuseumInfoView() {

    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null) {
            this.setMuseum_uid(parameter);
        }
        try {
            ResponseEntity<MuseumInfoResponse> response = vaadinService.getMuseumInfo(this.getMuseum_uid());
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                MuseumInfoResponse museum = response.getBody();
                List<ShowResponse> shows = response.getBody().getShows();
                H3 name = new H3(museum.getName());
                name.getStyle().set("padding-left", "32px").set("margin-top", "10px");

                Paragraph paragraph = new Paragraph(museum.getDescription());
                paragraph.getStyle().set("padding-left", "32px").set("margin-top", "-10px");

                Span email = new Span("Email: " + museum.getEmail());
                Span address = new Span(museum.getAddress().getCity() + " " + museum.getAddress().getStreet() + " " + museum.getAddress().getHouse());

                Span type = new Span("Тип музея: " + museum.getType().toLowerCase());
                type.getStyle().set("padding-left", "32px").set("margin-top", "-16px");


                VirtualList<ShowResponse> showList = new VirtualList<>();
                showList.setItems(shows);


                VerticalLayout contacts = new VerticalLayout(email, address);
                contacts.setSpacing(false);
                contacts.setPadding(false);

                Span legal_entity_name = new Span(museum.getLegalEntityName());
                Span inn = new Span("ИНН: " + museum.getInn());
                Span orgn = new Span("ОГРН: " + museum.getOgrn());

                VerticalLayout content = new VerticalLayout(legal_entity_name, inn, orgn);
                content.setSpacing(false);
                content.setPadding(false);

                Details contactDetails = new Details("Контакты", contacts);
                contactDetails.setOpened(false);
                contactDetails.getStyle().set("padding-left", "32px");

                Details infoDetails = new Details("Информация о ЮЛ", content);
                infoDetails.setOpened(false);
                infoDetails.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                add(name, paragraph, type, showList, contactDetails, infoDetails);
            }
        } catch (Exception e) {
            Notification.show("Что-то пошло не так");
        }
    }

    public String getMuseum_uid() {
        return museum_uid;
    }

    public void setMuseum_uid(String museum_uid) {
        this.museum_uid = museum_uid;
    }
}
