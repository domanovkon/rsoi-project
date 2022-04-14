package com.domanov.vaadin;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;

public class AppShell extends Div implements AppShellConfigurator {

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addFavIcon("icon", "icons/icon.png", "192x192");
        settings.addLink("shortcut icon", "icons/favicon.ico");
    }
}