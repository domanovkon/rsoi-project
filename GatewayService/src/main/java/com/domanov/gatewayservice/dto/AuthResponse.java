package com.domanov.gatewayservice.dto;

public class AuthResponse {

    private String jwt;

    private Boolean darkTheme;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Boolean getDarkTheme() {
        return darkTheme;
    }

    public void setDarkTheme(Boolean darkTheme) {
        this.darkTheme = darkTheme;
    }
}
