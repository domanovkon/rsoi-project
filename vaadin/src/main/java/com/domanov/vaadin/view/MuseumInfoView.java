package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.*;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

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
                showList.getStyle().set("height", "288px");
                if (shows.size() == 0) {
                    showList.setVisible(false);
                }

                Button changeMuseumButton = new Button("Изменить");
                changeMuseumButton.setHeight("32px");
                changeMuseumButton.setWidth("103px");

                Button deleteMuseumButton = new Button("Удалить", e -> {
                    ResponseEntity<Object> responseEntity = vaadinService.removeMuseum(museum_uid);
                    if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                        Notification.show("Музей успешно удален");
                        UI.getCurrent().navigate(MuseumView.class);
                    } else {
                        Notification.show("Что-то пошло не так");
                    }
                });
                deleteMuseumButton.setHeight("32px");
                deleteMuseumButton.setWidth("90px");


                Dialog dialog = new Dialog();
                VerticalLayout dialogLayout = createDialogLayout(dialog, museum.getMuseum_uid(), showList, shows);
                dialog.add(dialogLayout);
                dialog.add(dialogLayout);
                Button addShowButton = new Button("Добавить выставку", e -> dialog.open());
                addShowButton.setHeight("32px");
                addShowButton.setWidth("175px");

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.getStyle().set("padding-left", "32px");
                horizontalLayout.add(changeMuseumButton, deleteMuseumButton, addShowButton);

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

                add(name, paragraph, type);
                ResponseEntity<UserResponse> useResponse = vaadinService.getUser();
                if (useResponse.getBody().getRole().equals("ADMIN")) {
                    add(horizontalLayout);
                }
                add(showList, contactDetails, infoDetails);
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
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = createDialogLayout(dialog, showResponse.getName(), showResponse.getPrice(), showResponse.getShow_uid());
        dialog.add(dialogLayout);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button buyButton = new Button("Билеты", e -> dialog.open());
        buyButton.setHeight("32px");
        buyButton.setWidth("90px");
        Button removeShowButton = new Button("Удалить", e -> {
            ResponseEntity<Object> responseEntity = vaadinService.removeShow(showResponse.getShow_uid());
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                Notification.show("Выставка успешно удалена");
                UI.getCurrent().getPage().reload();
//                UI.getCurrent().navigate(MuseumInfoView.class);
            } else {
                Notification.show("Что-то пошло не так");
            }
        });
        removeShowButton.setHeight("32px");
        removeShowButton.setWidth("90px");
        horizontalLayout.add(buyButton);
        ResponseEntity<UserResponse> response = vaadinService.getUser();
        if (response.getBody().getRole().equals("ADMIN")) {
            horizontalLayout.add(removeShowButton);
        }
        infoLayout.add(horizontalLayout);

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

    public VerticalLayout createDialogLayout(Dialog dialog, String showName, Integer price, String show_uid) {
        H3 headline = new H3("Покупка билета");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        AtomicInteger sum = new AtomicInteger();
        Span name = new Span(showName);
        IntegerField integerField = new IntegerField();
        integerField.setLabel("Количество");
        integerField.setMin(1);
        integerField.setMax(10);
        integerField.setValue(1);
        integerField.setHasControls(true);

        IntegerField sumField = new IntegerField();
        sumField.setLabel("Сумма");
        sumField.setMin(price);
        sumField.setValue(price);
        sumField.setReadOnly(true);
        sumField.setHasControls(false);

        integerField.addValueChangeListener(e -> {
            sum.set(price * integerField.getValue());
            sumField.setValue(sum.intValue());
        });

        VerticalLayout fieldLayout = new VerticalLayout(name, integerField, sumField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        Button buyButton = new Button("Купить", buttonClickEvent -> {
            try {
                TicketBuyRequest ticketBuyRequest = new TicketBuyRequest();
                ticketBuyRequest.setShow_uid(show_uid);
                ticketBuyRequest.setAmount(integerField.getValue());
                ticketBuyRequest.setPrice(price);
                ResponseEntity<Object> response = vaadinService.buyTicket(ticketBuyRequest);
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    Notification.show("Билеты успешно приобретены!");
                }
            } catch (Exception e) {
                Notification.show("Что-то пошло не так");
            }
            dialog.close();
        });
        buyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                buyButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "500px").set("max-width", "100%");
        return dialogLayout;
    }

    public VerticalLayout createDialogLayout(Dialog dialog, String museum_uid, VirtualList<ShowResponse> showList, List<ShowResponse> shows) {
        H3 headline = new H3("Добавление выставки");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        TextField name = new TextField("Название*");
        name.setClearButtonVisible(true);
        name.setMaxLength(80);

        TextArea desc = new TextArea("Описание");
        desc.setMaxLength(600);
        desc.setClearButtonVisible(true);
        desc.setValueChangeMode(ValueChangeMode.EAGER);
        desc.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + 600);
        });

        TextField price = new TextField("Цена");
        price.setClearButtonVisible(true);
        price.setMaxLength(12);
        price.setPattern("^([1-9]).*[0-9]");
        price.setSuffixComponent(new Div(new Text("₽")));
        price.setErrorMessage("Указана некорректная цена");

        DatePicker startDate = new DatePicker("Дата начала");
        DatePicker endDate = new DatePicker("Дата окончания");
        startDate.setClearButtonVisible(true);
        endDate.setClearButtonVisible(true);
        startDate.setEnabled(false);
        endDate.setEnabled(false);

        startDate.addValueChangeListener(e -> {
            endDate.setMin(e.getValue());
        });
        endDate.addValueChangeListener(e -> {
            startDate.setMax(e.getValue());
        });

        Checkbox permanentExhibition = new Checkbox();
        permanentExhibition.setLabel("Постоянная выставка");
        permanentExhibition.setValue(true);
        permanentExhibition.addValueChangeListener(e -> {
            if (e.getValue()) {
                endDate.setValue(null);
                startDate.setValue(null);
            }
            endDate.setEnabled(!e.getValue());
            startDate.setEnabled(!e.getValue());
        });
        permanentExhibition.getStyle().set("margin-top", "13px");
        VerticalLayout fieldLayout = new VerticalLayout(name, desc, price, permanentExhibition, startDate, endDate);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        Button addButton = new Button("Добавить", buttonClickEvent -> {
            if (name.getValue().trim().isEmpty()) {
                Notification.show("Введите имя");
            } else if (price.getValue() == null || price.getValue().trim().isEmpty() || price.isInvalid()) {
                Notification.show("Укажите корректную цену");
            } else if (!permanentExhibition.getValue() && (startDate.getValue() == null || endDate.getValue() == null)) {
                Notification.show("Укажите корректную дату");
            } else {
                ShowResponse showResponse = new ShowResponse();
                showResponse.setName(name.getValue());
                showResponse.setDescription(desc.getValue());
                showResponse.setPermanentExhibition(permanentExhibition.getValue());
                showResponse.setPrice(Integer.parseInt(price.getValue()));
                if (!permanentExhibition.getValue()) {
                    showResponse.setStartDate(LocalDateTime.of(startDate.getValue(), LocalTime.of(0, 0, 0)));
                    showResponse.setEndDate(LocalDateTime.of(endDate.getValue(), LocalTime.of(0, 0, 0)));
                }
                showResponse.setMuseum_uid(museum_uid);
                ResponseEntity<ShowResponse> responseEntity = vaadinService.createShow(showResponse);
                if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                    Notification.show("Выставка успешно добавлена");
//                    UI.getCurrent().navigate();
                    UI.getCurrent().getPage().reload();
//                    UI.getCurrent().navigate(MuseumInfoView.class);
//                    shows.add(responseEntity.getBody());
//                    showList.setItems(shows);
//                    showList.setRenderer(showCardRenderer);
                    showList.setVisible(true);
                } else {
                    Notification.show("Что-то пошло не так");
                }
                dialog.close();
            }
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                addButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "500px").set("max-width", "100%");
        return dialogLayout;
    }

    public String getMuseum_uid() {
        return museum_uid;
    }

    public void setMuseum_uid(String museum_uid) {
        this.museum_uid = museum_uid;
    }
}
