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
    
    public void create(String conditions, double availableBalance, double overdraftLimit, double fee){
        this.conditions = conditions;
        this.availableBalance = availableBalance;
        this.overdraftLimit = overdraftLimit;
        this.fee = fee;
    }  

    @Override
    // Used when creating new accounts and saving them to the client account files
    public void saveToFile(BufferedWriter bw){
        try {
            bw.write(super.outputToFile() +", "+ this.output());
        } catch (IOException ex) {
            Logger.getLogger(ClsISAAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String output(){
        return this.conditions+", "+this.availableBalance+", "+this.overdraftLimit+", "+this.fee;
    }
    
    public String outputTransactions(){
        return (super.outputToFile() +", "+ this.output());
    }
    
    /*
    public ClsCurrentAccount unimportantMethod(){
        return this;
    }
    */
        
    // This method is called when a new transaction has been made (deposit or withdraw).
    private void saveToTransactionFile(ClsTransaction transaction){
        transactionsList.removeLineFromFile(super.accountHolder.getCustomerDetails()[0]+super.accountHolder.getCustomerDetails()[1], "EmptyLine", transaction);
        //System.out.println("transactions_"+super.accountHolder.getCustomerDetails()[0]+super.accountHolder.getCustomerDetails()[1]+".txt\n");
        //System.out.println("Empty Line removed.\n");
        transactionsList.saveToFile(super.accountHolder.getCustomerDetails()[0]+super.accountHolder.getCustomerDetails()[1], transaction);
        // Testing
        //System.out.println("Output successful.\n");
        System.gc();
    }
    
    public void depositMonthlyInterest(){
        // fix
        transactions++;
        //double amount = monthlyInterest stuff
        //transactionsList.add(new ClsTransaction(super.makeDate(), amount, this, this, this.balance));
        //this.saveToTransactionFile();
    }
 
    @Override
    public void calculateCharges(){
        // fix 
    }
 
    @Override
    public boolean deposit(double amount) {
        boolean success = false;
        if (amount > 0) {
            this.balance = this.balance + amount;
            transactions++;

            //ClsTransaction transaction = new ClsTransaction(super.makeDate(), "In", amount, this, this, this.balance);
            ClsTransaction transaction = new ClsTransaction(super.makeDate(), "In", amount,  new ClsCurrentAccount(), this.balance);
            
            if (transactionsList == null){
                transactionsList = new ClsTransactionList();
                transactionsList.add(transaction);
                success = true;
            }
            else {
                transactionsList.add(transaction);
                success =  true;
            }
            
            this.saveToTransactionFile(transaction);

        } else {
            success = false;
        }
        return success;
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
            //ClsTransaction transaction = new ClsTransaction(super.makeDate(), "Out", amount, this, this, this.balance);
            ClsTransaction transaction = new ClsTransaction(super.makeDate(), "Out", amount, new ClsCurrentAccount(), this.balance);
            
            transactionsList.add(transaction);
            this.saveToTransactionFile(transaction);
            return true;
        }
        else{
            this.balance = this.balance - amount;
            transactions++; 
            //ClsTransaction transaction = new ClsTransaction(super.makeDate(), "Out", amount, this, this, this.balance);
            ClsTransaction transaction = new ClsTransaction(super.makeDate(), "Out", amount,  new ClsCurrentAccount(), this.balance);
            
            transactionsList.add(transaction);
            this.saveToTransactionFile(transaction);
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
            //ClsTransaction transaction = new ClsTransaction(super.makeDate(), "Out", amount, this, this, this.balance);
            ClsTransaction transaction = new ClsTransaction(super.makeDate(), "Out", amount,  new ClsCurrentAccount(), this.balance);
            
            transactionsList.add(transaction);
            endOfMonthSummary(src);
            this.saveToTransactionFile(transaction);
            
        }
    }
    

}
