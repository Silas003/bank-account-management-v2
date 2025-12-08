package com.models;

public abstract class Customer {

    private String customerId;
    private String name;
    private int age;
    private String contact;
    private String type;
    private String address;
    static int customerCounter;


    Customer() {
        setCustomerId();
    }

    @Override
    public String toString() {
        return String.format("%s ", this.getName());
    }


    Customer(String name, int age, String contact, String address,String type) {
        this();
        setName(name);
        setAge(age);
        setContact(contact);
        setAddress(address);
        setType(type);
    }


    public String getCustomerId() {
        return this.customerId;
    }


    public void setCustomerId() {
        this.customerId = "CUS00" + ++customerCounter;
    }

    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public  void setType(String type){
        this.type = type;
    }


    public abstract String displayCustomerDetails();

    public abstract String getCustomerType();
}
