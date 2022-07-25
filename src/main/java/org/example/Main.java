package org.example;

import javax.sound.midi.Soundbank;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Bank bank = null;

        Scanner sc = new Scanner(System.in);

        Bank HDFC = new Bank("HDFC");
        Bank SBI = new Bank("SBI");
        Bank BOB = new Bank("BOB");

        System.out.println("*** Welcome ***");
        System.out.println("Select a bank\n1) HDFC \n2) SBI \n3) BOB\n");

        String selectedBank;
        do{
            System.out.print("Enter here the Bank number :");
            selectedBank = sc.nextLine();
            if(selectedBank.equals("1") || selectedBank.equals("2") || selectedBank.equals("3")){ // Even if user enters characters system wont crash

                if(selectedBank.equals("1")){
                    bank = HDFC;
                }
                else if(selectedBank.equals("2")){
                    bank = SBI;
                }
                else if (selectedBank.equals("3")) {
                    bank = BOB;
                }
                break;
            } else {System.out.printf("Choose a valid digit between (1-3) of the corresponding bank \n");
            }
        }while (true);



        User curUser= null;

        while(true){

            String existing = checkExistingUser(bank,sc);

            if(existing.equals("1")){
                createUser(bank,sc);
            } else if (existing.equals("2")) {
                curUser = Main.mainMenuPrompt(bank,sc);
                Main.printMenu(curUser, sc, bank);
            }

        }

    }

    private static void createUser(Bank bank, Scanner sc) {

        String firstName;
        String lastName;
        String userPin;
        System.out.print("Enter your First Name : ");
        firstName = sc.nextLine();
        System.out.print("Enter your Last Name : ");
        lastName = sc.nextLine();


        do {
            System.out.println("Enter a pin of length 4, remember it for further use : ");
            userPin = sc.nextLine();
            if (userPin.length() < 4 || userPin.length() > 4) {
                System.out.printf("Pin should be less of length 4\n");
            }
        }while (userPin.length() < 4 || userPin.length() > 4) ;

        User user1 = bank.addUser(firstName, lastName, userPin);

    }

    private static String checkExistingUser(Bank bank, Scanner sc) {

        System.out.printf("\nWelcome to %s \n", bank.getBankName());

        String i;
        do {
            System.out.println("Enter 1 to create new account or 2 if existing user");
            i = sc.nextLine();
            if (i.equals("1") || i.equals("2")) {// Even if user enters characters system wont crash
                break;
            }else System.out.printf("Choose a valid digit 1 or 2 \n");
        } while (true);

        return i;
    }

    private static void printMenu(User curUser, Scanner sc, Bank bank) {
        curUser.showSummary();

        int choice;

        do{

            System.out.printf("\nWhat would you like to do %s %s? \n",curUser.getFirstName(), curUser.getLastName());


            System.out.println("1) Show Transaction History");
            System.out.println("2) Add Account");
            System.out.println("3) Withdraw Money");
            System.out.println("4) Deposit Money");
            System.out.println("5) Transfer Money" );
            System.out.println("6) Quit Session");
            System.out.println();
            System.out.print("Enter Choice : ");
            choice = sc.nextInt();

            if(choice<1 || choice>6){
                System.out.println("You are asked to put a number between 1-6 including 1 and 5");
            }

        }while(choice<1 || choice>6);

        switch (choice){
            case 1:
                Main.showTransactions(curUser,sc);
                break;

            case 2:
                Main.addAccount(curUser, sc, bank);
                break;

            case 3:
                Main.withdrawMoney(curUser,sc);
                break;

            case 4:
                Main.depositMoney(curUser,sc);
                break;

            case 5:
                Main.transferfunds(curUser,sc);
                break;

        }

        if(choice != 6){
         printMenu(curUser,sc, bank);
        }
        else {
            System.out.println("***  Thank you for your visit   ***");
            System.exit(0);}

    }

    private static void addAccount(User curUser, Scanner sc, Bank bank) {

        System.out.println("Select account type :\n1) Savings\n2) Current\n3) Checking");
        String userEnteredAccType;
        sc.nextLine();

        do{
            System.out.println("Enter type (1-3) :");
            userEnteredAccType = sc.nextLine();
            if(userEnteredAccType.equals("1") || userEnteredAccType.equals("2")|| userEnteredAccType.equals("3")){
                // Even if user enters characters system won't crash
                if(userEnteredAccType.equals("1")){
                    userEnteredAccType = "Savings";
                } else if (userEnteredAccType.equals("2")){
                    userEnteredAccType = "Current";
                } else if (userEnteredAccType.equals("3")) {
                    userEnteredAccType = "Checking";
                }
                break;
            }else System.out.printf("Choose a valid digit between (1-3) \n");
        }while(true);



        Account acc1 = new Account(userEnteredAccType, curUser, bank);

    }


    private static void showTransactions(User curUser, Scanner sc) {

        int theAcct;

        do{
            System.out.printf("Choose the number (1-%d) of which you need the transaction history",curUser.noOfAccount());
            theAcct = sc.nextInt()-1;
            if(theAcct<0 || theAcct >= curUser.noOfAccount()){
                System.out.printf("Choose a valid digit between (1-%d)", curUser.noOfAccount());
            }
        }while (theAcct<0 || theAcct >= curUser.noOfAccount());

        curUser.printTransactionHistory(theAcct);
    }

    private static User mainMenuPrompt(Bank bank, Scanner sc) {

        String userId;

        String pin;

        User authUser;

        do {

            System.out.printf("Enter user ID : ");
            userId = sc.nextLine();

            System.out.print("Enter pin : ");
            pin = sc.nextLine();

            authUser = bank.login(userId, pin);

            if (authUser == null) {
                System.out.println("Incorrect User ID / PIN  Enter again");
            }
        } while (authUser == null);

        return authUser;

    }

    private static void transferfunds(User curUser, Scanner sc) {

        /**
         * Amount of 'amount' to transfer from 'fromAcct' to 'toAcct' and balance of that account to know before transferring
         */
        int fromAcct;
        int toAcct;
        double amount;
        double accBal;

        //select account number
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer from: ", curUser.noOfAccount());
            fromAcct = sc.nextInt() - 1;

            if (fromAcct < 0 || fromAcct >= curUser.noOfAccount()) {
                System.out.printf("Choose a valid digit between (1-%d): ", curUser.noOfAccount());
            }
        } while (fromAcct < 0 || fromAcct >= curUser.noOfAccount());

        accBal = curUser.getAcctBalance(fromAcct);

        // select receiver account number
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer to", curUser.noOfAccount());
            toAcct = sc.nextInt() - 1;

            if (toAcct < 0 || toAcct >= curUser.noOfAccount()) {
                System.out.printf("Choose a valid digit between (1-%d)", curUser.noOfAccount());
            }
        } while (toAcct < 0 || toAcct >= curUser.noOfAccount());


        //amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accBal) {
                System.out.printf("Amount must not be greater than balance of $%.02f \n", accBal);
            }

        } while (amount < 0 || amount > accBal);

        //finally, transaction

        curUser.addActTransfer(fromAcct,-1*amount,String.format("Transfer to account %s",curUser.getAccountNumberOf(toAcct)));

        curUser.addActTransfer(toAcct,amount,String.format("Transferred from account %s",curUser.getAccountNumberOf(fromAcct)));

    }

    private static void withdrawMoney(User curUser, Scanner sc) {

        int fromAcct;
        String memo;
        double amount;
        double accBal=0.0;

        //select account number
        do {
            System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", curUser.noOfAccount());
            fromAcct = sc.nextInt() - 1;

            if (fromAcct < 0 || fromAcct >= curUser.noOfAccount()) {
                System.out.printf("Choose a valid digit between (1-%d): ", curUser.noOfAccount());
            }
        } while (fromAcct < 0 || fromAcct >= curUser.noOfAccount());

        accBal = curUser.getAcctBalance(fromAcct);

        //Amount to withdraw
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accBal) {
                System.out.printf("Amount must not be greater than balance of $%.02f\n", accBal);
            }

        } while (amount < 0 || amount > accBal);

        sc.nextLine();

        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        curUser.addActTransfer(fromAcct, -1*amount, memo);

    }

    private static void depositMoney(User curUser, Scanner sc) {
        int toAcct;
        String memo;
        double amount;
        double accBal;

        //select account number
        do {
            System.out.printf("Enter the number (1-%d) of the account you want to deposit to: ", curUser.noOfAccount());
            toAcct = sc.nextInt() - 1;

            if (toAcct < 0 || toAcct >= curUser.noOfAccount()) {
                System.out.printf("Choose a valid digit between (1-%d): ", curUser.noOfAccount());
            }
        } while (toAcct < 0 || toAcct >= curUser.noOfAccount());

        accBal = curUser.getAcctBalance(toAcct);

        //amount to deposit
        do {
            System.out.printf("Enter the amount to deposit : $", accBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } //else if (amount > accBal) {
              //  System.out.printf("Amount must not be greater than balance of $%s.02F \n", accBal);
            //}

        } while (amount < 0);

        sc.nextLine();

        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        curUser.addActTransfer(toAcct, amount, memo);
        System.out.println("*** Deposited successfully ***");
    }

}






