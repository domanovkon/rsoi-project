package com.domanov.sessionservice.dto;

import java.time.LocalDateTime;

public class UserStatDto {

    private String name;

    private String username;

    private String user_uid;

    private LocalDateTime registerDate;

    private String stringRegisterDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public String getStringRegisterDate() {
        return stringRegisterDate;
    }

    public void setStringRegisterDate(String stringRegisterDate) {
        this.stringRegisterDate = stringRegisterDate;
    }
}
