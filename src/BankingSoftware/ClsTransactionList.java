/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        src.setText("");
        for (ClsTransaction transaction : transactions) {
                transaction.display(src);
                src.append("\n");
        }
    }

    public void add(ClsTransaction newTransaction){
        transactions.add(newTransaction);
    }
    
    public boolean checkSize(){
        if(this.transactions != null){
            return true;
        }
        else return false;
    }
    
    public void saveToFile(String name, ClsTransaction transaction){
        BufferedWriter out = null;
        String filename = "";
        switch (transaction.getAccType()) {
            case "Current Account":
                filename = ("transactions_" + name + "_CA.txt");
                break;
            case "ISA Account":
                filename = ("transactions_" + name + "_ISA.txt");
                break;
            case "Saving Account":
                filename = ("transactions_" + name + "_SA.txt");
                break;
            default:
                filename = null;
        }
        
        try {
            out = new BufferedWriter (new FileWriter(filename, true));
            transaction.saveToFile(out); 
            out.newLine();
            out.close();
        } catch (IOException ioe1) { System.out.println("IO Problem: " + ioe1); }
    }
    
    public void removeLineFromFile(String name, String lineToRemove, ClsTransaction transaction) {
        BufferedReader br;
        PrintWriter pw = null;
        String filename;
        
        switch (transaction.getAccType()) {
            case "Current Account":
                filename = ("transactions_" + name + "_CA.txt");
                break;
            case "ISA Account":
                filename = ("transactions_" + name + "_ISA.txt");
                break;
            case "Saving Account":
                filename = ("transactions_" + name + "_SA.txt");
                break;
            default:
                filename = null;
        }
        
        try {
            File inFile = new File(filename);
            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }
            // Construct the new file that will later be renamed to the original filename. 
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            br = new BufferedReader(new FileReader(filename));
            pw = new PrintWriter(new FileWriter(tempFile));
            String line;
            // Read from the original file and write to the new unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(lineToRemove)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();
            System.gc();

            // Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile)) {
                System.out.println("Could not rename file");
            }
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
            if (pw != null) {
                pw.close();
            }
        }
    }
    
    public void loadFromFile(String filename){
        
    }
}

