package com.domanov.museumservice.model;

import javax.persistence.*;

@Entity
@Table(name = "museum_type")
public class MuseumType {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "type", length = 80, nullable = false)
    private String type;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
