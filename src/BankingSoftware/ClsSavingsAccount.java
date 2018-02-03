/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedWriter;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsSavingsAccount extends ClsAccount{
    private double withdrawLimit;
    
    public ClsSavingsAccount(){
        super();
        create(200);
    }
    
    
    public ClsSavingsAccount(
        String bankCode, int accountNo, double balance, String nameOfBank, double rate, int transactions, ClsCustomer accountHolder,
        double withdrawLimit
    ){
        super(bankCode, accountNo, balance, nameOfBank, rate, transactions, accountHolder);
        create(withdrawLimit);
    }
    
    public void display(){
        
    }
    
    public void create(double withdrawLimit){
        this.withdrawLimit = withdrawLimit;
    }
    
    public void depositMonthlyInterest(){
        
        transactions++;
    }

    @Override
    public void calculateCharges(){
        //fix
    }
    /*
        A withdrawal is not allowed during period of 90 days started from the opening account. Each
    withdrawal during this period generates a £10.00 fee. Also, if at least one withdrawal occurred the interest rate
    is reduced by 0.49%. There is no restrictions on how much money can be deposited. The fee for a month is
    levied at the end of saving period (90 days) and counts as a transaction. The interest for a month is calculated
    at the end of the saving period (90 days) and counts as a transaction.
    */
    
    /*
    public void saveToFile(BufferedWriter bw){
        
    }
    */
    
    @Override
    public boolean deposit(double amount){
        boolean depositSuccessful = false;
        if(amount > 0){
            balance += amount;
            transactions++;
        }
        return depositSuccessful;
    }
 
    @Override
    public boolean withdraw(double amount){
        long days = transactionDate.getTime() - openingDate.getTime();
        //testing
        System.out.println(days);
        
        // could create error
        if(rate > 0 && days<=90){
            rate = rate - 0.49;
        }
        if(amount > 0 && days <= 90){
            balance = balance - amount - 10.00;
            transactions++;
        }
        
        return false;
    }
    
    @Override
    public void endOfMonthUtil(JTextArea src) {
        long days = transactionDate.getTime() - openingDate.getTime();
        if (days >= 31) {
            balance *= rate;
            transactions++;
            endOfMonthSummary(src);
        }
    }
}
