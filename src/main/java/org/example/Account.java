package org.example;

import java.util.ArrayList;

public class Account {

    /**
     * 1) Type of Account(Savings,current account,OD account,salary account)
     */
    private String accountName;

    /**
     *2)  Account Number
     */
    private String accountNumber;

    /**
     * 3) To know user details of that account
     */
    private User holderOfAccount;

    /**
     * 4) Transactions of that specific account
     */
    private ArrayList<Transaction> transaction;

    public String getAccountNumber() {
        return accountNumber;
    }

    public Account(String accountName, User holderOfAccount, Bank bank){

        this.accountName = accountName;

        this.holderOfAccount = holderOfAccount;

        this.accountNumber = bank.getNewAccountNumber();

        this.transaction = new ArrayList<Transaction>();

        holderOfAccount.addAccount(this);

        bank.addAccount(this);

    }


    // called in User.showSummary, to show the balance of that particular account
    public String getSummaryOfThisAccount() {
        double balance = this.getBalance();

        if(balance>=0) {
            return String.format("Balance $%.02f : Account type : %s\n",  balance, this.accountName);
        }
        else {
            return String.format("Balance $(%.02f) : Type %s", balance, this.accountName);// negative balance
        }

    }

    //called in Account.getSummaryOfThisAccount to add balance from all the transactions (including withdrawal and deposits)
    public double getBalance() {
        double bal = 0;

        for (Transaction t:
                this.transaction) {bal+= t.getAmount();
        }
        return bal;
    }

    //called in User.printTransactionHistory which gets the account number, here we want all details of that transaction
    public void printHistory() {
        System.out.printf("Transaction History of account %s \n",this.accountNumber);
        for(int t = this.transaction.size()-1; t>=0; t--){
            System.out.println(this.transaction.get(t).getSummaryLine());
        }
        System.out.println();
    }

    //called in User.addActTransfer to deduct or add balance
    public void addTransaction(double amount, String memo) {
        //Create new transaction object and add it to list

        Transaction newTransaction = new Transaction(amount,this, memo);
        this.transaction.add(newTransaction);
    }



}
