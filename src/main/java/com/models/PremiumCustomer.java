package com.models;

public class PremiumCustomer extends Customer {

    private final double minimumBalance = 10000;

    public PremiumCustomer(String name, int age, String contact, String address,String type) {
        super(name, age, contact, address,type);
    }


    public double getMinimumBalance() {
        return this.minimumBalance;
    }


    public String displayCustomerDetails() {
        return String.format("%s |%s |%s  |%s |%s\n",
                this.getCustomerId(), this.getName(), this.getCustomerType(),this.getContact(),
                this.getAddress());
    }


    @Override
    public String getCustomerType() {
        return "Premium";
    }


    public boolean hasWaivedFees() {
        return true;
    }
}
