/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * 
 * @author ee16ttz
 */
public class ClsISAAccount extends ClsAccount{
    private double maxDepositPerYear;
    private double depositedThisYear;
    
    public ClsISAAccount(){
        super();
        create(3250.0, 0.0);
    }
    
    public ClsISAAccount( String type,
            String sortCode, int accountNo, double balance, String nameOfBank, double rate, int transactions, ClsCustomer accountHolder,
            double maxDepositPerYear, double depositedThisYear
    ){
        super(type, sortCode, accountNo, balance, nameOfBank, rate, transactions, accountHolder);
        create(maxDepositPerYear, depositedThisYear);
    }
    
    public void display(/*int option, JLabel src*/){
        /*
        if(option==1){
            src.setText("Not enough funds.");
        }
        else if(option==2){
            
        }
        */
    }  
    
    public void create(double maxDepositPerYear, double depositedThisYear){
        this.maxDepositPerYear = maxDepositPerYear;
        this.depositedThisYear = depositedThisYear;
    }    
    
    @Override
    public void saveToFile(BufferedWriter bw){
        try {
            bw.write(super.outputToFile() +", "+ this.maxDepositPerYear+", "+depositedThisYear);
        } catch (IOException ex) {
            Logger.getLogger(ClsISAAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void depositYearlyInterest(){
        long days = transactionDate.getTime() - openingDate.getTime();
        if(days >= 365) {
            balance = balance * rate;
            transactions++;
        }
    }   
    
    @Override
    public void calculateCharges(){
    }
 
    @Override
    public boolean deposit(double amount){
        boolean depositSuccessful = false;
        //possible error 
        if ((amount + this.depositedThisYear) < this.maxDepositPerYear) {
            balance += amount;
            this.depositedThisYear += amount;
            transactions++;
            depositSuccessful = true;
        }
        else{
            depositSuccessful = false;
        }
        return depositSuccessful;
    }
    
    @Override
    public boolean withdraw(double amount){
        boolean withdrawSuccessful = false;
        if(balance > 0){
            balance -= amount;
            transactions++;
            withdrawSuccessful = true;
        }
        else{
            withdrawSuccessful = false;
        }
        return withdrawSuccessful;
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

