/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public abstract class ClsAccount {
    protected String type;
    protected String sortCode;
    protected int accountNo;
    protected double balance;
    protected String nameOfBank;
    protected double rate;
    //protected Date openingDate;
    protected Date transactionDate;
    protected Date openingDate;
    protected int transactions;
    
    private boolean joint;
    private Date lastReportedDate;
    
    private ClsCustomer accountHolder;
    protected ClsCurrentAccount coHolder;
    
    public ClsAccount() {
        create(" ", 0, 0.0d, " ", 1.20, 0, accountHolder);
        transactionDate = new Date(01/01/1900);
    }
    
    public ClsAccount(String sortCode, int accountNo, double balance, String nameOfBank, double rate, /*Date openingDate,*/int transactions, ClsCustomer accountHolder){
        create(sortCode, accountNo, balance, nameOfBank, rate, transactions, accountHolder);
    }
    
    protected abstract boolean deposit(double amount);
    protected abstract boolean withdraw(double amount);
    protected abstract void calculateCharges();
    protected abstract void endOfMonthUtil(JTextArea src);
    protected abstract void saveToFile(BufferedWriter bw);
    
    public void create(String sortCode, int accountNo, double balance, String nameOfBank, double rate, int transactions, ClsCustomer accountHolder){
        this.sortCode = sortCode;
        this.accountNo = accountNo;
        this.balance = balance;
        this.nameOfBank = nameOfBank;
        this.rate = rate;
        this.transactions = transactions;
        this.accountHolder = accountHolder;
        //this.lastReportedDate = new Date(01/01/1900);
        //this.openingDate = new Date(01/01/1900);
        //this.lastReportedDate = " ";
        //this.openingDate = " ";
    }
    
    public void display(JTextArea src){
        src.append(
                "Type: "                + this.type             + "\n"  +
                "Sort Code: "           + this.sortCode         + "\n"  +
                "Account Number: "      + this.accountNo        + "\n"  +
                "Balance: "             + this.balance          + "\n"  +
                "NameOfBank: "          + this.nameOfBank       + "\n"  +
                "Rate: "                + this.rate             + "\n"  +
                "Client Name: "         + this.accountHolder.getCustomerDetails()[0] + " " + this.accountHolder.getCustomerDetails()[1]  + "\n" //+
                //"Last reported Date:"   + this.lastReportedDate + "\n"  +        
                //"Opening Date: "        + this.openingDate      + "\n\n"  
        );
    }
    
    /*
    public void transfer(in toAccount){     //WHAT TYPE?
    }
    */
    
    /*
    public void saveToFile(BufferedWriter bwfCustomerList){
        try {
            //possibly more attributes should be written out 
            // and array of accounts should be written out... but how am i supposed to load in variable amount of fields from a file???
            bwfCustomerList.write(", " + sortCode + ", " + accountNo + ", " + balance + ", " + nameOfBank + "]"); 
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
            ioe1.printStackTrace();
            try {
                if (bwfCustomerList != null) {
                    bwfCustomerList.close();
                }
            } catch (IOException ioe2) {
                ioe2.printStackTrace();
                System.out.println("IO Problem: " + ioe2);
            }
        }
    }
    */
    
    public boolean isTheSame(ClsAccount anAccount) {
        if(
            sortCode.equals(anAccount.sortCode) &&
            this.accountNo == (anAccount.accountNo) &&
            this.balance == (anAccount.balance) &&
            nameOfBank.equals(anAccount.nameOfBank)        
        ){
            return true;
        } 
        else {
            return false;
        }
    }
    
    public void printStatement(){
        
    }
    
    public void lastReportedDate(Date aLastReportedDate){
        
    }
    
    public double getBalance(){
        return this.balance;
    }
    
    public String outputDetails(){
        return ("Sort code: " + this.sortCode + "\n" + "Account number: " + this.accountNo);
    }
    
    /*
    public void setCustomer(ClsCustomer src){
        customer = src;
    }
    */
    
    protected void endOfMonthSummary(JTextArea src){
        src.append(
                "Transactions:" + transactions  + "\n" + 
                "Balance: "     + balance       + "\n"  
                // Shouldnt balance of each account be different?
        );
        transactions = 0;
    }
    
    // Method for testing line 177 in ClsCustomer
    protected String outputToFile(){
        return (this.sortCode +", "+ this.accountNo +", "+ this.balance +", "+ this.nameOfBank +", "+ this.rate +", "+ this.transactions /* +", "+ this.openingDate + this.lastReportedDate */);
    }

}
