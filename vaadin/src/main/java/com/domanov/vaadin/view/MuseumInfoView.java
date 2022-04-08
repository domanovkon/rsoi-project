package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.MuseumInfoResponse;
import com.domanov.vaadin.dto.ShowResponse;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
                showList.setRenderer(showCardRenderer);
                showList.getStyle().set("padding-left", "32px");
//                showList.getStyle().set("height", "100%");
//                showList.setHeight("300px");

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

    private ComponentRenderer<Component, ShowResponse> showCardRenderer = new ComponentRenderer<>(showResponse -> {
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);

        String avtTxt = "Временная";
        if (showResponse.getPermanentExhibition()) {
            avtTxt = "Постоянная";
        }
        Avatar avatar = new Avatar(avtTxt);
        avatar.setHeight("64px");
        avatar.setWidth("64px");
        avatar.setColorIndex(1);

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);
        infoLayout.getElement().appendChild(ElementFactory.createStrong(showResponse.getName()));
        infoLayout.add(new Div(new Text(showResponse.getDescription())));
        infoLayout.add(new Div(new Text("Цена: " + showResponse.getPrice().toString() + "₽")));

        if (!showResponse.getPermanentExhibition()) {
            avatar.setColorIndex(0);
            VerticalLayout contactLayout = new VerticalLayout();
            contactLayout.setSpacing(false);
            contactLayout.setPadding(false);
            String start = DateTimeFormatter.ofPattern("d MMMM yyyy")
                            .withLocale(new Locale("ru"))
                            .format((showResponse.getStartDate().toLocalDate()));
            String end = DateTimeFormatter.ofPattern("d MMMM yyyy")
                            .withLocale(new Locale("ru"))
                            .format((showResponse.getEndDate().toLocalDate()));
            contactLayout.add(new Div(new Text("Начало: " + start)));
            contactLayout.add(new Div(new Text("Окончание: " + end)));
            infoLayout.add(new Details("Дата проведения", contactLayout));
        }

        cardLayout.add(avatar, infoLayout);
        return cardLayout;
    });

    public String getMuseum_uid() {
        return museum_uid;
    }

    public void setMuseum_uid(String museum_uid) {
        this.museum_uid = museum_uid;
    }
}
