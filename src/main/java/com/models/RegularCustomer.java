package com.models;

public class RegularCustomer extends Customer {

    public RegularCustomer(String name, int age, String contact, String address,String type) {
        super(name, age, contact, address, type);
    }

    @Override
    public String displayCustomerDetails() {
        return String.format("%s |%s |%s  |%s |%s\n",
                this.getCustomerId(), this.getName(), this.getCustomerType(),this.getContact(),
                this.getAddress());
    }

    @Override
    public String getCustomerType() {
        return "regular";
    }
}
