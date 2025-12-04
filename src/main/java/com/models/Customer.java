
package com.models;

/*
 * Abstract base class representing a customer in the bank account management system.
 *
 * This class provides the core structure for all customer types (Regular and Premium).
 * It maintains customer identification, personal information, and contact details.
 * Customer IDs are automatically generated in the format "CUS" followed by a sequential counter.
 *
 * Customer information includes:
 * - Unique customer ID
 * - Name
 * - Age
 * - Contact information (phone number)
 * - Address
 *
 * Subclasses must implement:
 * - displayCustomerDetails() - Return customer details for display
 * - getCustomerType() - Return the customer type identifier
 */
public abstract class Customer {

    // Unique identifier for the customer in format "CUS" + counter
    private String customerId;

    // Customer's full name
    private String name;

    // Customer's age in years
    private int age;

    // Customer's contact phone number
    private String contact;

    private String type;

    // Customer's physical address
    private String address;

    // Static counter for generating unique customer IDs
    static int customerCounter;

    /*
     * Default constructor that initializes the customer and generates a unique customer ID.
     * Increments the static customer counter to ensure each customer receives a unique identifier.
     */
    Customer() {
        setCustomerId();
    }

    /*
     * Returns a string representation of the customer.
     * Currently returns only the customer's name.
     */
    @Override
    public String toString() {
        return String.format("%s ", this.getName());
    }

    /*
     * Constructs a customer with all required information.
     * Initializes the customer with a unique ID, then sets all provided fields.
     */
    Customer(String name, int age, String contact, String address,String type) {
        this();
        setName(name);
        setAge(age);
        setContact(contact);
        setAddress(address);
        setType(type);
    }

    // Retrieves the customer's unique identifier
    public String getCustomerId() {
        return this.customerId;
    }

    // Generates and sets a unique customer ID based on the current customer counter
    public void setCustomerId() {
        this.customerId = "CUS00" + ++customerCounter;
    }

    // Retrieves the customer's name
    public String getName() {
        return this.name;
    }

    // Sets the customer's name
    public void setName(String name) {
        this.name = name;
    }

    // Retrieves the customer's age
    public int getAge() {
        return this.age;
    }

    // Sets the customer's age
    public void setAge(int age) {
        this.age = age;
    }

    // Retrieves the customer's contact information
    public String getContact() {
        return this.contact;
    }

    // Sets the customer's contact information
    public void setContact(String contact) {
        this.contact = contact;
    }

    // Retrieves the customer's address
    public String getAddress() {
        return this.address;
    }

    // Sets the customer's address
    public void setAddress(String address) {
        this.address = address;
    }

    public  void setType(String type){
        this.type = type;
    }

    /*
     * Abstract method to return customer details for display purposes.
     * Subclasses should implement this to return a formatted representation of all customer information.
     */
    public abstract String displayCustomerDetails();

    /*
     * Abstract method to return the customer type identifier.
     * Subclasses must return their specific customer type (e.g., "Regular", "Premium").
     */
    public abstract String getCustomerType();
}
