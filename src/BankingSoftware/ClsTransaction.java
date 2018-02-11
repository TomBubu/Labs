/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsTransaction {
    
    private Date date;
    private double amount;
    //private String reference;
    private ClsAccount receiver;
    private ClsAccount sender;
    private double balance;
    
    private ClsAccount account;
    
    public ClsTransaction(){
        this.date = new Date(01/01/1900);
        this.amount = 0.0d;
        //this.reference = " ";
        this.receiver = new ClsCurrentAccount();
        this.sender = new ClsCurrentAccount();
        this.balance = 0.0d;
    }
    
    public ClsTransaction(Date date, double amount,/* String reference,*/ ClsAccount receiver, ClsAccount sender, double balance){
        create(date, amount,/* reference,*/ receiver, sender, balance);
    }
    
    public void create(Date date, double amount,/* String reference,*/ ClsAccount receiver, ClsAccount sender, double balance){
        this.date = date;
        this.amount = amount;
        //this.reference = reference;
        this.receiver = receiver;
        this.sender = sender;
        this.balance = balance;
    }
    
    public void display(JTextArea src){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        src.append(
                sdf.format(this.date) + " | " 
                        + this.amount + " | " 
                        //+ this.reference + " | "
                        + this.receiver + " | " 
                        + this.sender + " | " 
                        + this.balance
        );
    }
}
