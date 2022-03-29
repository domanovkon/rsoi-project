package com.domanov.vaadin.view;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Главная страница")
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        Image mainImage = new Image("matryoshka-doll.png", "матрешка");
        mainImage.setHeight("250px");
        mainImage.setHeight("250px");
        add(mainImage);
        add(new H1("Музеи России"));
        add(new H4("Покупайте билеты в любой музей России!"));
        Image imageArt = new Image("art1.png", "art");
        imageArt.setWidth("80px");
        imageArt.setHeight("80px");
        Image imageSculpture = new Image("sculpture.png", "sculpture");
        imageSculpture.setWidth("80px");
        imageSculpture.setHeight("80px");
        Image imageBook = new Image("book.png", "book");
        imageBook.setWidth("80px");
        imageBook.setHeight("80px");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(imageArt, imageBook, imageSculpture);
        add(horizontalLayout);
    }
}
