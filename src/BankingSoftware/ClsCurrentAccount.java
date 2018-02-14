/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        //transactionsList = new ClsTransactionList();
        create(" ", 0.00, 100.00, 25.00);
    }
       
    public ClsCurrentAccount( String type,
            String sortCode, int accountNo, double balance, String nameOfBank, double rate, int transactions, ClsCustomer accountHolder,
            String conditions, double availableBalance, double overdraftLimit, double fee
    ){
        super(type, sortCode, accountNo, balance, nameOfBank, rate, transactions, accountHolder);
        create(conditions, availableBalance, overdraftLimit, fee);
    }
    
    public void display(){
        
    }
    
    private Date makeDate(){
        // Create today's date for transactions
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        // Testing
        //System.out.println(sqlDate);
        return sqlDate;
    }
    
    public void create(String conditions, double availableBalance, double overdraftLimit, double fee){
        this.conditions = conditions;
        this.availableBalance = availableBalance;
        this.overdraftLimit = overdraftLimit;
        this.fee = fee;
    }  

    @Override
    public void saveToFile(BufferedWriter bw){
        try {
            bw.write(super.outputToFile() +", "+ this.conditions+", "+this.availableBalance+", "+this.overdraftLimit+", "+this.fee);
        } catch (IOException ex) {
            Logger.getLogger(ClsISAAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void depositMonthlyInterest(){
        // fix
        transactions++;
        //double amount = monthlyInterest stuff
        //transactionsList.add(new ClsTransaction(makeDate(), amount, this, this, this.balance));
    }
 
    @Override
    public void calculateCharges(){
        // fix 
    }
 
    @Override
    public boolean deposit(double amount) {
        if (amount > 0) {
            this.balance = this.balance + amount;
            transactions++;

            ClsTransaction transaction = new ClsTransaction(makeDate(), "In", amount, this, this, this.balance);
            if (transactionsList == null){
                transactionsList = new ClsTransactionList();
                transactionsList.add(transaction);
                return true;
            }
            else {
                transactionsList.add(transaction);
                return true;
            }

        } else {
            return false;
        }
    }
    
    @Override
    public boolean withdraw(double amount) {
        // Gives error: abstract method withdraw(double) in ClsAccount cannot be accessed directly 
        //super.withdraw(value);

        if((this.balance - amount) < ((this.overdraftLimit)*(-1)) ) {

            // 100 - 300 = -200  < -100
            //withdraw(this.fee); // will result in cyclic error, stacoverflow error
            this.balance = (this.balance - amount) - this.fee;

            //endMonthUtil();
            transactions++;
            transactionsList.add(new ClsTransaction(makeDate(), "Out", amount, this, this, this.balance));
            return true;
        }
        else{
            this.balance = this.balance - amount;
            transactions++; 
            transactionsList.add(new ClsTransaction(makeDate(), "Out", amount, this, this, this.balance));
            return true;
        }
    }
    
    @Override
    public void endOfMonthUtil(JTextArea src){
        long days = transactionDate.getTime() - openingDate.getTime();
        if(days >= 31){
            this.balance = this.balance * super.rate;
            // 5 = 5 * 1.20  
            // balance will be 6.
            
            // possible error
            double amount = this.balance * super.rate;
            
            transactions++;
            transactionsList.add(new ClsTransaction(makeDate(), "In", amount, this, this, this.balance));
            endOfMonthSummary(src);
        }
    }


}
