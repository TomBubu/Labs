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
public class ClsCurrentAccount extends ClsAccount{
    private String conditions;
    private double availableBalance;
    private double overdraftLimit;
    private double fee;
    
    public ClsCurrentAccount(){
        //create();
        super();
        create(" ", 0.00, 100.00, 25.00);
    }
       
    public ClsCurrentAccount(
            String bankCode, int accountNo, double balance, String nameOfBank, double rate, int transactions, ClsCustomer accountHolder,
            String conditions, double availableBalance, double overdraftLimit, double fee
    ){
        super(bankCode, accountNo, balance, nameOfBank, rate, transactions, accountHolder);
        create(conditions, availableBalance, overdraftLimit, fee);
    }
    
    public void display(){
        
    }
    
    public void create(String conditions, double availableBalance, double overdraftLimit, double fee){
        this.conditions = conditions;
        this.availableBalance = availableBalance;
        this.overdraftLimit = overdraftLimit;
        this.fee = fee;
    }
    
    /*
    public String display(){
        return super.displayNewAccount();
    }
    */
    
    //task 1, step 5

    /*
    public void saveToFile(BufferedWriter bw){
        
    }
    */
    
    public void depositMonthlyInterest(){
        // fix
        transactions++;
    }
 
    @Override
    public void calculateCharges(){
        // fix 
    }
 
    @Override
    public boolean deposit(double amount){  
        boolean depositSuccessful = false;
        if(amount > 0){
            //super.transactions.addTransaction;
            this.balance = this.balance + amount;
            transactions++;
            depositSuccessful = true;
        }
        else{
            depositSuccessful = false;
        }
        return depositSuccessful;
    }
    
    @Override
    public boolean withdraw(double amount) {
        boolean withdrawSuccessful = false;
        // Gives error: abstract method withdraw(double) in ClsAccount cannot be accessed directly 
        //super.withdraw(value);
        if((this.balance - amount)< this.overdraftLimit) {
            withdraw(this.fee);
            //endMonthUtil();
            //super.transactions.addTransaction;
            transactions++;
            withdrawSuccessful = true;
        }
        else{
            this.balance = this.balance - amount;
            transactions++; 
            withdrawSuccessful = true;
        }
        return withdrawSuccessful;
    }
    
    @Override
    public void endOfMonthUtil(JTextArea src){
        long days = transactionDate.getTime() - openingDate.getTime();
        if(days >= 31){
            balance *= rate;
            transactions++;
            endOfMonthSummary(src);
        }
    }

}
