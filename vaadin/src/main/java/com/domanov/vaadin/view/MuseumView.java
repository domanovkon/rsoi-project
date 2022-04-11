package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.MuseumPageResponse;
import com.domanov.vaadin.dto.MuseumResponse;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.List;

@Route(value = "museums", layout = MainLayout.class)
@PageTitle("Музеи")
public class MuseumView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    private int totalAmountOfPages;

    private int itemsPerPage = 5;

    private int currentPageNumber = 1;

    public MuseumView() {
    }

    @PostConstruct
    public void init() {
        Grid<MuseumResponse> grid = new Grid<>();
        try {
            this.getStyle().set("padding", "0");
            ResponseEntity<MuseumPageResponse> museums = vaadinService.getMuseums(currentPageNumber, itemsPerPage);
            totalAmountOfPages = museums.getBody().getTotalElements();
            List<MuseumResponse> initialItems = museums.getBody().getItems();
            Integer totalAmountOfItems = museums.getBody().getTotalElements();
            if (totalAmountOfItems % itemsPerPage == 0) {
                totalAmountOfPages = totalAmountOfItems / itemsPerPage;
            } else {
                totalAmountOfPages = totalAmountOfItems / itemsPerPage + 1;
            }
            grid.addColumn(MuseumResponse::getName).setHeader("Название").setWidth("200px");
            grid.addColumn(MuseumResponse::getType).setHeader("Тип").setWidth("200px");
            grid.addColumn(MuseumResponse::getCity).setHeader("Город").setWidth("200px");
            grid.addColumn(MuseumResponse::getDescription).setHeader("Описание").setFlexGrow(10);
            grid.setWidth("100%");
            grid.setWidthFull();
            grid.getStyle().set("margin", "0");
            grid.setHeightByRows(true);
            H5 currPage = new H5(String.valueOf(currentPageNumber));
            currPage.getStyle().set("margin-top", "0.8em");
            grid.setItems(initialItems);
            Button previousButton = new Button("Предыдущая страница", e -> {
                if (currentPageNumber <= 1) {
                    return;
                }
                ResponseEntity<MuseumPageResponse> museumsNext = vaadinService.getMuseums(--currentPageNumber, itemsPerPage);
                List<MuseumResponse> prevPageItems = museumsNext.getBody().getItems();
                grid.setItems(prevPageItems);
                currPage.setText(String.valueOf(currentPageNumber));
            });
            Button nextButton = new Button("Следующая страница", e -> {
                if (currentPageNumber >= totalAmountOfPages) {
                    return;
                }
                ResponseEntity<MuseumPageResponse> museumsNext = vaadinService.getMuseums(++currentPageNumber, itemsPerPage);
                List<MuseumResponse> nextPageItems = museumsNext.getBody().getItems();
                grid.setItems(nextPageItems);
                currPage.setText(String.valueOf(currentPageNumber));
            });
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.add(previousButton, currPage, nextButton);
            horizontalLayout.getStyle().set("margin-left", "16px");
            grid.addItemClickListener(item -> {
                UI.getCurrent().navigate("museum/" + item.getItem().getMuseum_uid());
            });
            add(grid, horizontalLayout);

        } catch (Exception e) {
            Notification.show("Что-то пошло не так");
        }
    }
}
