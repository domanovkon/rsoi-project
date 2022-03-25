package com.domanov.museumservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "museum", schema = "public")
public class Museum {

    @Id
    @GeneratedValue
    private Integer id;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "museum_uid", nullable = false, unique = true)
    private UUID museum_uid;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "museum_type_id", referencedColumnName = "id")
    private MuseumType museumType;

    @Column(name = "legal_entity_name", length = 255)
    private String legalEntityName;

    @Column(name = "inn", length = 12)
    private String inn;

    @Column(name = "ogrn", length = 13)
    private String ogrn;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getMuseum_uid() {
        return museum_uid;
    }

    public void setMuseum_uid(UUID museum_uid) {
        this.museum_uid = museum_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public MuseumType getMuseumType() {
        return museumType;
    }

    public void setMuseumType(MuseumType museumType) {
        this.museumType = museumType;
    }

    public String getLegalEntityName() {
        return legalEntityName;
    }

    public void setLegalEntityName(String legalEntityName) {
        this.legalEntityName = legalEntityName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }
}
