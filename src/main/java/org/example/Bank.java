package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String bankName;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    public Bank(String bankName){
        this.bankName = bankName;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();

    }

    public String getBankName() {
        return bankName;
    }

    public User addUser(String firstName, String lastName, String pin){

        User newUser = new User(firstName, lastName,pin, this);
        this.users.add(newUser);

        Account newAccount = new Account("Savings", newUser, this);

        return newUser;
    }


    //User's UUID created in Bank so as to maintain it Unique
    public String getNewUserID() {

        String UUID = "";
        int len = 6;
        Random rng = new Random();
        boolean nonUnique;

        do {
            for (int i = 0; i < len; i++) {
                UUID += ((Integer) rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (User u :
                    this.users) {
                if (UUID.compareTo(u.getUserUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique == true);

        return UUID;

       }

       //Users account number generated uniquely
    public String getNewAccountNumber() {
        String accountNumber = "";
        int len = 10;
        Random rng = new Random();
        boolean nonUnique;

        do {
            for (int i = 0; i < len; i++) {
                accountNumber += ((Integer) rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (Account a :
                    this.accounts) {
                if (accountNumber.compareTo(a.getAccountNumber()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique == true);

        return accountNumber;

    }

    //called in Account, to add that account in list of accounts of Bank
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    //Function for Login
    public User login(String UUID, String pin){

        for (User u:
                this.users) {
            if( u.validatePin(pin) && (u.getUserUUID().compareTo(UUID) == 0 ) ){
                return u;
            }
        }

        return null;
    }


}


