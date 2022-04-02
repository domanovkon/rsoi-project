package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.MuseumPageResponse;
import com.domanov.vaadin.dto.MuseumResponse;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
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
    private int itemsPerPage = 10;
    private int currentPageNumber = 1;

    public MuseumView() {
    }

    @PostConstruct
    public void init() {
//        try {
//            ResponseEntity<MuseumPageResponse> museums = vaadinService.getMuseums();
//            museums.getBody();
//        } catch (Exception e) {
//            Notification.show("Что-то пошло не так");
//        }
//        MuseumGridView museumGridView = new MuseumGridView();
//        add(museumGridView);
        Grid<MuseumResponse> grid = new Grid<>();
        try {
            this.getStyle().set("padding", "0")
            .set("padding-top", "16px");
            ResponseEntity<MuseumPageResponse> museums = vaadinService.getMuseums(currentPageNumber, itemsPerPage);
            totalAmountOfPages = museums.getBody().getTotalElements();
            List<MuseumResponse> initialItems = museums.getBody().getItems();
            grid.addColumn(MuseumResponse::getName).setHeader("Название").setWidth("200px");
            grid.addColumn(MuseumResponse::getType).setHeader("Тип").setWidth("200px");
            grid.addColumn(MuseumResponse::getCity).setHeader("Город").setWidth("200px");
            grid.addColumn(MuseumResponse::getDescription).setHeader("Описание").setFlexGrow(10);
            grid.setWidth("100%");
            grid.setWidthFull();
            grid.getStyle().set("margin", "0");
            grid.setHeightByRows(true);
            grid.setItems(initialItems);
//            Button nextButton = new Button("Следующая страница", e -> {
//                if (currentPageNumber >= totalAmountOfPages) {
//                    return;
//                }
//                ResponseEntity<MuseumPageResponse> museumsNext = vaadinService.getMuseums(++currentPageNumber, itemsPerPage);
//                List<MuseumResponse> nextPageItems = museumsNext.getBody().getItems();
//                grid.setItems(nextPageItems);
//            });
//            Button previousButton = new Button("Предыдущая страница", e -> {
//                if (currentPageNumber <= 1) {
//                    return;
//                }
//                ResponseEntity<MuseumPageResponse> museumsNext = vaadinService.getMuseums(--currentPageNumber, itemsPerPage);
//                List<MuseumResponse> prevPageItems = museumsNext.getBody().getItems();
//                List<Bean> prevPageItems = RestApi.getPageItems(--currentPageNumber, totalAmountOfPages, itemsPerPage);
//                grid.setItems(prevPageItems);
//            });
            add(grid);

        } catch (Exception e) {
            Notification.show("Что-то пошло не так");
        }
    }
}
