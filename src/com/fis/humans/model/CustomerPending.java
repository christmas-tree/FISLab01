package com.fis.humans.model;


public class CustomerPending extends Customer {
    private int status;

    public CustomerPending(int customerId, String name, int birthYear, int income, String city, String district, int status) {
        super(customerId, name, birthYear, income, city, district);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
