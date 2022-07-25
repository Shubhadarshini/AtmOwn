package org.example;

import java.util.Date;

public class Transaction {

    /**
     * 1) Amount to be transferred
     */
    private double amountToTransfer;

    /**
     * 2) Timestamp of transfer
     */
    private Date timestamp;

    /**
     * 3) purpose of transfer
     */
    private String memo;

    /**
     * 4) Account details of the account from which money transferred
     */
    private Account inAccount;


    public Transaction(double amountToTransfer, Account inAccount){
        this.amountToTransfer = amountToTransfer;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, Account inAccount,String memo){
        this(amount, inAccount);
        this.memo = memo;

    }


    //called in accounts.getBalance
    public double getAmount() {
        return this.amountToTransfer;
    }

    public String getSummaryLine() {
        if(amountToTransfer>=0){
            return String.format("%s : $%s.02f : %s",this.timestamp.toString(), this.amountToTransfer, this.memo);
        }
        else {
            return String.format("%s : $(%s.02f) : %s",this.timestamp.toString(), this.amountToTransfer, this.memo);
        }
    }
}
