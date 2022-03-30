package com.domanov.sessionservice.dto;

import com.domanov.sessionservice.model.Role;
import io.jsonwebtoken.Jwt;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

public class UserResponse {

    private Integer id;

    private UUID user_uid;

    private String name;

    private String surname;

    private String login;

    private String role;

    private String jwt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(UUID user_uid) {
        this.user_uid = user_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
