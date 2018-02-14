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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
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
        /*
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
        */
        filename = ("transactions_" + name + ".txt");
        
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
        
        /*
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
        */
        filename = ("transactions_" + name + ".txt");
        
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
    
    public void loadFromFile(String filename, ClsCustomer aCustomer) throws ParseException {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                for (String word : line.split(", ")) { 
                    //if I do ",\n" then 1 customer per line //if ", " then 1 customer, 4 lines
                    words.add(word);
                }
            }
            String[] DetailsArray =  words.toArray(new String[words.size()]);
            transactions.clear();
            
            //I need to increment i by 4 each time, because one client is made of 4 entries
            for (int i = 0; i < words.size() - 15; i += 15) {

                
                // Define format
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                // Get strings, which will be dates
                String strDate = DetailsArray[i];
                // Parse strings to java.util.Date
                Date date = sdf.parse(strDate);
                // Convert java.util.Date to sql.Date. This strips the time part off
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                             
                
                ClsCurrentAccount sender = new ClsCurrentAccount(
                        DetailsArray[i+4],
                        DetailsArray[i+5], //sort code         2
                        Integer.parseInt(DetailsArray[i + 6]), //accountNo         3
                        Double.parseDouble(DetailsArray[i +7]), //balance           4
                        DetailsArray[i + 8], //nameOfBank        5
                        Double.parseDouble(DetailsArray[i + 9]), //rate              6
                        Integer.parseInt(DetailsArray[i + 10]), //transactions      7 
                        aCustomer, //ClsCustomer accountHolder     8
                        DetailsArray[i + 11], //conditions        9
                        Double.parseDouble(DetailsArray[i + 12]), //availableBalance  10
                        Double.parseDouble(DetailsArray[i + 13]), //overdraftLimit    11
                        Double.parseDouble(DetailsArray[i + 14]) //fee
                );
                     
                ClsTransaction newTransaction = new ClsTransaction(
                        sqlDate,                                    // date
                        DetailsArray[i+1],                          // type
                        Double.parseDouble(DetailsArray[i+2]),      // amount
                        sender,                                     // cls ?? Account sender
                        Double.parseDouble(DetailsArray[i+3])      // balance  
                );
                transactions.add(newTransaction);
            }
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
        }
    }
    
 
}

