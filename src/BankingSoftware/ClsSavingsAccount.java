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
public class ClsSavingsAccount extends ClsAccount{
    private double withdrawLimit;
    
    public ClsSavingsAccount(){
        super();
        create(200);
    }
    
    
    public ClsSavingsAccount(String type,
        String bankCode, int accountNo, double balance, String nameOfBank, double rate, int transactions, ClsCustomer accountHolder,
        double withdrawLimit
    ){
        super(type, bankCode, accountNo, balance, nameOfBank, rate, transactions, accountHolder);
        create(withdrawLimit);
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
    
    @Override
    public void saveToFile(BufferedWriter bw){
        try {
            bw.write(super.outputToFile() +", "+ this.withdrawLimit);
        } catch (IOException ex) {
            Logger.getLogger(ClsISAAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean deposit(double amount){
        boolean depositSuccessful = false;
        if(amount > 0){
            balance += amount;
            transactions++;
            
            
            //ClsTransaction transaction = new ClsTransaction(super.makeDate(), "In", amount, this, new ClsCurrentAccount(), this.balance);
            ClsTransaction transaction = new ClsTransaction(super.makeDate(), "In", amount, new ClsCurrentAccount(), this.balance);
            if (transactionsList == null){
                transactionsList = new ClsTransactionList();
                transactionsList.add(transaction);
                
                depositSuccessful = true;
            }
            else {
                transactionsList.add(transaction);
                depositSuccessful =  true;
            }
            
            this.saveToTransactionFile(transaction);
            
        }
        return depositSuccessful;
    }
    
    
    // CR.
    private void saveToTransactionFile(ClsTransaction transaction){
        transactionsList.removeLineFromFile(super.accountHolder.getCustomerDetails()[0]+super.accountHolder.getCustomerDetails()[1], "EmptyLine", transaction);
        //System.out.println("transactions_"+super.accountHolder.getCustomerDetails()[0]+super.accountHolder.getCustomerDetails()[1]+".txt\n");
        //System.out.println("Empty Line removed.\n");
        transactionsList.saveToFile(super.accountHolder.getCustomerDetails()[0]+super.accountHolder.getCustomerDetails()[1], transaction);
        // Testing
        //System.out.println("Output successful.\n");
        System.gc();
    }
    
    
 
    @Override
    public boolean withdraw(double amount){
        
        transactionDate = new Date();
        openingDate = new Date();
        long days = (transactionDate.getTime() - openingDate.getTime()) / (1000 * 60 * 60 * 24);
        //Date date = new Date();
        //java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        // testing
        //System.out.println(days);
        
        // could create error
        if(rate > 0 && days<=90){
            rate = rate - 0.49;
        }
        if(amount > 0 && days >= 90){
            balance = balance - amount - 10.00;
            transactions++;
            ClsTransaction transaction = new ClsTransaction(super.makeDate(), "In", amount, new ClsCurrentAccount(), this.balance);
            if (transactionsList == null){
                transactionsList = new ClsTransactionList();
                transactionsList.add(transaction);
                
                return true;
            }
            else {
                transactionsList.add(transaction);
                return true;
            }
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
