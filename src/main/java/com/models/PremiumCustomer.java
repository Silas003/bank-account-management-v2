package com.models;

/*
 * Represents a premium customer in the bank account management system.
 *
 * Premium customers receive enhanced benefits including fee waivers and
 * higher minimum balance requirements. They must maintain a minimum balance
 * of $10,000 to qualify for premium status.
 *
 * Premium customer benefits:
 * - Monthly fees are waived on checking accounts
 * - Higher minimum balance requirement ($10,000)
 * - Access to premium account features
 *
 * Premium customers are required to make an initial deposit of at least
 * $10,000 when opening an account to maintain their premium status.
 */
public class PremiumCustomer extends Customer {

    // Minimum balance required to maintain premium CUSTOMER status
    private double minimumBalance = 10000;

    /*
     * Constructs a premium customer with the provided information.
     * Initializes the customer with premium status and sets the minimum balance requirement to $10,000.
     */
    public PremiumCustomer(String name, int age, String contact, String address,String type) {
        super(name, age, contact, address,type);
    }

    // Retrieves the minimum balance requirement for premium customers
    public double getMinimumBalance() {
        return this.minimumBalance;
    }

    /*
     * Sets the minimum balance requirement for premium customers.
     * Allows flexibility in adjusting premium customer requirements if business rules change.
     */
    public void setMinimumBalance(double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    /*
     * Returns this customer object for display purposes.
     */
    @Override
    public String displayCustomerDetails() {
        return String.format("%s |%s |%s  |%s |%s\n",
                this.getCustomerId(), this.getName(), this.getCustomerType(),this.getContact(),
                this.getAddress());
    }

    /*
     * Returns the customer type identifier.
     */
    @Override
    public String getCustomerType() {
        return "Premium";
    }

    /*
     * Indicates whether fees are waived for this premium customer.
     * Premium customers always have fees waived as a benefit of their status.
     */
    public boolean hasWaivedFees() {
        return true;
    }
}
