package com.models;

public class RegularCustomer extends Customer {

    public RegularCustomer(String name, int age, String contact, String address,String type) {
        super(name, age, contact, address, type);
    }

    public RegularCustomer(String CustomerId, String name, int age, String contact, String address, String type) {
        super(CustomerId,name, age, contact, address, type);
    }

    public String displayCustomerDetails() {
        return String.format("%s |%s |%s  |%s |%s\n",
                this.getCustomerId(), this.getName(), this.getCustomerType(),this.getContact(),
                this.getAddress());
    }


    public String getCustomerType() {
        return "regular";
    }
}
