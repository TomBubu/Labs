/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedWriter;
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
    protected Date transactionDate;
    protected Date openingDate;
    protected int transactions;
    protected ClsCurrentAccount coHolder;
    protected ClsTransactionList transactionsList = null;
    
    private boolean joint;
    private Date lastReportedDate;
    protected ClsCustomer accountHolder;
    
    
    public ClsAccount() {
        create(" ", " ", 0, 0.0d, " ", 1.20, 0, accountHolder);
        transactionDate = new Date();//01/01/1900
        openingDate = new Date();
    }
    
    public ClsAccount(String type, String sortCode, int accountNo, double balance, String nameOfBank, double rate, /*Date openingDate,*/int transactions, ClsCustomer accountHolder){
        create(type, sortCode, accountNo, balance, nameOfBank, rate, transactions, accountHolder);
    }
    
    protected abstract boolean deposit(double amount);
    protected abstract boolean withdraw(double amount);
    protected abstract void calculateCharges();
    protected abstract void endOfMonthUtil(JTextArea src);
    protected abstract void saveToFile(BufferedWriter bw);
    
    public void create(String type, String sortCode, int accountNo, double balance, String nameOfBank, double rate, int transactions, ClsCustomer accountHolder){
        this.type = type;
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
    public void transfer(in toAccount){
    }
    */

    public boolean isTheSame(ClsAccount anAccount) {
        if(
            this.sortCode.equals(anAccount.sortCode) &&
            this.accountNo == (anAccount.accountNo) &&
            this.balance == (anAccount.balance) &&
            this.nameOfBank.equals(anAccount.nameOfBank)        
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
        );
        transactions = 0;
    }
    
    // Method for testing line 177 in ClsCustomer
    protected String outputToFile(){
        return (this.type+ ", "+this.sortCode +", "+ this.accountNo +", "+ this.balance +", "+ this.nameOfBank +", "+ this.rate +", "+ this.transactions /* +", "+ this.openingDate + this.lastReportedDate */);
    }

    public Date makeDate(){
        // Create today's date for transactions
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        // Testing
        //System.out.println(sqlDate);
        return sqlDate;
    }
    
    /*
    public ClsCurrentAccount unimportantMethodX(){
        ClsCurrentAccount object1 = new ClsCurrentAccount();
        return object1.unimportantMethod();
    }
    */
}
