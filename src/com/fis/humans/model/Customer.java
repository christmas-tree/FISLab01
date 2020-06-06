package com.fis.humans.model;


public class Customer {

    private int customerId;
    private String name;
    private int birthYear;
    private int income;
    private String city;
    private String district;

    public Customer(int customerId, String name, int birthYear, int income, String city, String district) {
        this.customerId = customerId;
        this.name = name;
        this.birthYear = birthYear;
        this.income = income;
        this.city = city;
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }


    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}
