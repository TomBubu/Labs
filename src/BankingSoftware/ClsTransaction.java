/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsTransaction {
    
    private Date date;
    private String type;
    private double amount;
    //private String reference;
    private ClsAccount receiver;
    private ClsAccount sender;
    private double balance;
    
    public ClsTransaction(){
        create(new Date(01/01/1900), " ", 0.0d, new ClsCurrentAccount(), new ClsCurrentAccount(), 0.0d );
    }
    
    public ClsTransaction(Date date, String type, double amount,/* String reference,*/ ClsAccount receiver, ClsAccount sender, double balance){
        create(date, type, amount,/* reference,*/ receiver, sender, balance);
    }
    
    public void create(Date date, String type, double amount,/* String reference,*/ ClsAccount receiver, ClsAccount sender, double balance){
        this.date = date;
        this.type = type;
        this.amount = amount;
        //this.reference = reference;
        this.receiver = receiver;
        this.sender = sender;
        this.balance = balance;
    }
    
    public void display(JTextArea src){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        src.append(
                        "\t \n" +
                        sdf.format(this.date) + " | " + "Type: " + this.type + " | " + "Amount: " + this.amount + " | " + "Balance: " + this.balance + " | \n"
                        //+ this.reference + " | "
                        // A client is receiver but they can have multiple accounts, so it is good to see which account it was received to.
                        + "\t Receiver: \n" + this.receiver.outputDetails() + " \n " 
                        + "\t Sender: \n" + this.sender.outputDetails() + " \n "
        );
    }
    
    private String output(){
        return (this.date + ", " + this.type + ", " + this.amount + ", " + this.balance + ", " + this.receiver.outputToFile() + ", " + this.sender.outputToFile());
    }
    
    public void saveToFile(BufferedWriter bw){
        try {
            bw.write(this.output());
            bw.write(System.getProperty("line.separator"));
            bw.write("EmptyLine");
            // System.gc();
        } catch (IOException ex) {
            Logger.getLogger(ClsTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
