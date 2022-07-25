package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    /**
     * 1) User's First Name
     */
    private String firstName;

    /**
     * 2) User's Last Name
     */
    private String lastName;

    /**
     * 3)User's Unique User Identifier
     */
    private String userUUID;

    /**
     * 4) User's pin hashed and stored in byte
     */
    private byte pinHash[];

    /**
     * 5) List of accounts for a user
     */
    private ArrayList<Account> accounts;

    public String getUserUUID() {
        return userUUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public User(String firstName, String lastName, String pin, Bank bank) {

        this.firstName = firstName;
        this.lastName = lastName;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error : NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        this.userUUID = bank.getNewUserID();

        this.accounts = new ArrayList<>();  // Empty list of account

        System.out.printf("New account for %s %s created with UUID %S\n", firstName, lastName, userUUID);

    }

    //called in Account to add that account in list of accounts of User
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    // validating pin, called in Banks's login function
    public boolean validatePin(String pin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("error : NoSUchALgo");
            System.exit(1);
        }

        return false;
    }

    // called in function of printMenu of Main.java, to show users account details
    public void showSummary() {

        for (Account a :
                this.accounts) {
            System.out.printf("Account number %s, %s ", a.getAccountNumber(), a.getSummaryOfThisAccount());
        }

    }

    //called in Main.showTransactions
    public int noOfAccount() {

        return this.accounts.size();
    }

    // called in Main.showTransactions
    public void printTransactionHistory(int theAcct) {

        this.accounts.get(theAcct).printHistory();
    }

    //called in Main.transferFunds to get balance
    public double getAcctBalance(int fromAcct) {

        return this.accounts.get(fromAcct).getBalance();
    }

    //called in Main.transferFunds to deduct or add balance
    public void addActTransfer(int fromAcct, double amount, String memo) {

        this.accounts.get(fromAcct).addTransaction(amount, memo);

    }

    public String getAccountNumberOf(int toAcct) {
        return this.accounts.get(toAcct).getAccountNumber();
    }
}


