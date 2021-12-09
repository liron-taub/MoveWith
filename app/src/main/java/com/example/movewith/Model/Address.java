package com.example.movewith.Model;

public class Address {
    public String city;
    public String street;
    public int number;

    public Address(String city, String street, int number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public Address() {

    }

    @Override
    public String toString() {
        return city + " " + number + ", " + city;
    }
}
