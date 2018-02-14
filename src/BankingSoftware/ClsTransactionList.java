/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsTransactionList {
    private ArrayList<ClsTransaction> transactions;

    
    ClsTransactionList(){
        transactions = new ArrayList<>();
    }
    
    public void display(JTextArea src){
        /*
        for (int i = 0; i<transactions.size(); i++){
                src.setText("");
                transactions.get(i).display(src);
                src.append("\n");
        }
        */

        src.setText("");
        for (ClsTransaction transaction : transactions) {
                transaction.display(src);
                src.append("\n");
        }
        
    }

    public void add(ClsTransaction newTransaction){
        transactions.add(newTransaction);
        //System.out.println(transactions.size());
    }
    
    public boolean checkSize(){
        if(this.transactions != null){
            return true;
        }
        else return false;
    }
}

