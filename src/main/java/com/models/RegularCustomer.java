
package com.models;

/*
 * Represents a regular customer in the bank account management system.
 *
 * Regular customers are the standard customer tier with no special benefits
 * or fee waivers. They are subject to standard account fees and minimum balance
 * requirements as defined by their account types.
 *
 * Regular customers:
 * - Pay standard monthly fees on checking accounts
 * - Must meet standard minimum balance requirements
 * - Do not receive fee waivers or premium benefits
 */
public class RegularCustomer extends Customer {

    /*
     * Constructs a regular customer with the provided information.
     */
    public RegularCustomer(String name, int age, String contact, String address) {
        super(name, age, contact, address);
    }

    /*
     * Returns this customer object for display purposes.
     */
    @Override
    Customer displayCustomerDetails() {
        return this;
    }

    /*
     * Returns the customer type identifier.
     */
    @Override
    String getCustomerType() {
        return "regular";
    }
}
