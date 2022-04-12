package com.domanov.vaadin.view;

import com.domanov.vaadin.dto.MuseumPageResponse;
import com.domanov.vaadin.dto.MuseumResponse;
import com.domanov.vaadin.service.VaadinService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

@Route(value = "museums", layout = MainLayout.class)
@PageTitle("Музеи")
public class MuseumView extends VerticalLayout {

    @Autowired
    private VaadinService vaadinService;

    private int totalAmountOfPages;

    private int itemsPerPage = 12;

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
            GridListDataView<MuseumResponse> dataView = grid.setItems(initialItems);
            MuseumFilter museumFilter = new MuseumFilter(dataView);
            Grid.Column<MuseumResponse>  name= grid.addColumn(MuseumResponse::getName); //.setHeader("Название").setWidth("200px");
            Grid.Column<MuseumResponse>  type= grid.addColumn(MuseumResponse::getType);//.setHeader("Тип").setWidth("200px");
            Grid.Column<MuseumResponse>  city= grid.addColumn(MuseumResponse::getCity);//.setHeader("Город").setWidth("200px");
            Grid.Column<MuseumResponse>  desc= grid.addColumn(MuseumResponse::getDescription);//.setHeader("Описание").setFlexGrow(10);
            grid.setWidth("100%");
            grid.setWidthFull();
            grid.getStyle().set("margin", "0").set("margin-top", "-12px");
            grid.getHeaderRows().clear();

            name.setWidth("200px");
            type.setWidth("200px");
            city.setWidth("200px");
            desc.setFlexGrow(10);
            HeaderRow headerRow = grid.appendHeaderRow();

            headerRow.getCell(name).setComponent(
                    createFilterHeader("Название", museumFilter::setName));
            headerRow.getCell(type).setComponent(
                    createFilterHeader("Тип", museumFilter::setType));
            headerRow.getCell(city).setComponent(
                    createFilterHeader("Город", museumFilter::setCity));
            grid.setHeightByRows(true);
            H5 currPage = new H5(String.valueOf(currentPageNumber));
            currPage.getStyle().set("margin-top", "0.8em");
//            grid.setItems(initialItems);
            Button previousButton = new Button("Предыдущая страница", e -> {
                if (currentPageNumber <= 1) {
                    return;
                }
                ResponseEntity<MuseumPageResponse> museumsNext = vaadinService.getMuseums(--currentPageNumber, itemsPerPage);
                List<MuseumResponse> prevPageItems = museumsNext.getBody().getItems();
                GridListDataView<MuseumResponse> prevDataView = grid.setItems(prevPageItems);
                MuseumFilter prevMuseumFilter = new MuseumFilter(prevDataView);
                headerRow.getCell(name).setComponent(
                        createFilterHeader("Название", prevMuseumFilter::setName));
                headerRow.getCell(type).setComponent(
                        createFilterHeader("Тип", prevMuseumFilter::setType));
                headerRow.getCell(city).setComponent(
                        createFilterHeader("Город", prevMuseumFilter::setCity));
//                grid.setItems(prevPageItems);
                currPage.setText(String.valueOf(currentPageNumber));
            });
            Button nextButton = new Button("Следующая страница", e -> {
                if (currentPageNumber >= totalAmountOfPages) {
                    return;
                }
                ResponseEntity<MuseumPageResponse> museumsNext = vaadinService.getMuseums(++currentPageNumber, itemsPerPage);
                List<MuseumResponse> nextPageItems = museumsNext.getBody().getItems();
                GridListDataView<MuseumResponse> nextDataView = grid.setItems(nextPageItems);
                MuseumFilter nextMuseumFilter = new MuseumFilter(nextDataView);
                headerRow.getCell(name).setComponent(
                        createFilterHeader("Название", nextMuseumFilter::setName));
                headerRow.getCell(type).setComponent(
                        createFilterHeader("Тип", nextMuseumFilter::setType));
                headerRow.getCell(city).setComponent(
                        createFilterHeader("Город", nextMuseumFilter::setCity));
//                grid.setItems(nextPageItems);
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

    private static Component createFilterHeader(String labelText,
                                                Consumer<String> filterChangeConsumer) {
        Label label = new Label(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static class MuseumFilter {
        private final GridListDataView<MuseumResponse> dataView;

        private String name;
        private String type;
        private String city;

        public MuseumFilter(GridListDataView<MuseumResponse> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setName(String name) {
            this.name = name;
            this.dataView.refreshAll();
        }

        public void setType(String type) {
            this.type = type;
            this.dataView.refreshAll();
        }

        public void setCity(String city) {
            this.city = city;
            this.dataView.refreshAll();
        }

        public boolean test(MuseumResponse museumResponse) {
            boolean matchesName = matches(museumResponse.getName(), name);
            boolean matchesType = matches(museumResponse.getType(), type);
            boolean matchesCity = matches(museumResponse.getCity(),
                    city);

            return matchesName && matchesType && matchesCity;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty() || value
                    .toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
}
