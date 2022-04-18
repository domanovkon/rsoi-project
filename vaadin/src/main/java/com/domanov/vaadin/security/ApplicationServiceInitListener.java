package com.domanov.vaadin.security;

import com.domanov.vaadin.service.VaadinService;
import com.domanov.vaadin.view.MainView;
import com.domanov.vaadin.view.login.LoginView;
import com.domanov.vaadin.view.register.RegisterView;
import com.vaadin.flow.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class ApplicationServiceInitListener implements VaadinServiceInitListener {

    @Autowired
    private VaadinService vaadinService;

    @Override
    public void serviceInit(ServiceInitEvent initEvent) {
        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                Cookie[] cookies = VaadinRequest.getCurrent().getCookies();
                if (cookies != null) {
                    String token = vaadinService.getJWT();
                    if (vaadinService.validate(token).getLogin() == null) {
                        if (enterEvent.getNavigationTarget().equals(RegisterView.class)) {
                            enterEvent.forwardTo(RegisterView.class);
//                            enterEvent.rerouteTo(RegisterView.class);
                        } else {
                            enterEvent.forwardTo(LoginView.class);
//                            enterEvent.rerouteTo(LoginView.class);
                        }
                    } else if (vaadinService.validate(token).getLogin() != null) {
                        if (enterEvent.getNavigationTarget().equals(RegisterView.class) ||
                                enterEvent.getNavigationTarget().equals(LoginView.class)) {
                            enterEvent.forwardTo(MainView.class);
                        }
                    }
                }
            });
        });

//        initEvent.getSource().addUIInitListener(uiInitEvent -> {
//            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
//                if (!(LoginView.class.equals(enterEvent.getNavigationTarget()) ||
//                        RegisterView.class.equals(enterEvent.getNavigationTarget()))) {
//                    StringBuilder jwt = new StringBuilder();
//                    Cookie[] cookies = VaadinRequest.getCurrent().getCookies();
//                    if (cookies != null) {
//                        String token = vaadinService.getJWT();
//                        if (token.trim().equals("Bearer")) {
//                            enterEvent.rerouteTo(LoginView.class);
//                        } else {
//                            enterEvent.rerouteTo(MainView.class);
//
//                        }
//                    }
//                }
//            });
//        });
    }
}
