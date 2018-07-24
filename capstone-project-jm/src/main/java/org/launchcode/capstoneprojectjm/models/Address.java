package org.launchcode.capstoneprojectjm.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Address {



    @Id
    @GeneratedValue
    private int address_id;

    private String streetAddress;

    private String city;

    private String zipCode;

    private String country;


    @OneToMany
    @JoinColumn(name = "address_id")
    private List<Event> events = new ArrayList<>();


    public Address() {}

    public Address(String streetAddress, String city, String state, String zipCode, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Address(String streetAddress) {
        this.streetAddress = streetAddress;
    }


    public int getAddressId() {
        return address_id;
    }

    public void setAddressId(int address_id) {
        this.address_id = address_id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
