package com.domanov.museumservice.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "city", length = 255, nullable = false)
    private String city;

    @Column(name = "street", length = 255, nullable = false)
    private String street;

    @Column(name = "house", length = 255, nullable = false)
    private String house;

    @OneToOne(mappedBy = "address")
    private Museum museum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }
}
