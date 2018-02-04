/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
//import java.util.Date;
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsCustomerList {
    private ArrayList<ClsCustomer> theClients = new ArrayList<>();

    public ClsCustomerList(){}

    public void display(JTextArea src) {
        for (ClsCustomer Client : theClients) {
                Client.displayCustomerDetails(src);
                Client.getCustomerAddress().display(src, 0);
                src.append("\n");
        }
    }
    
    /*
    public void displayAccountInformation(JTextArea src){
        for (ClsCustomer Client : theClients) {
                Client.getCustomerAccount().Display(src);
        }
    }
    */

    public boolean addCustomer(ClsCustomer newCustomer){
        int old_size = theClients.size();
        theClients.add(newCustomer);
        int new_size = theClients.size();
        
        return new_size > old_size;
    }
    
    public boolean deleteCustomer(String surname, String firstName, String DOB){
        int old_Array_size = theClients.size();
        int new_Array_size = old_Array_size;
        
        for (int i=0; i < theClients.size(); i++){
            
            if (    theClients.get(i).getCustomerDetails()[0].equals(firstName)  &&
                    theClients.get(i).getCustomerDetails()[1].equals(surname)    &&
                    theClients.get(i).getCustomerDetails()[2].equals(DOB)
            ){
                new_Array_size--;
                theClients.remove(i);
            }
        }
        return (new_Array_size < old_Array_size);
    }    
    
    public void saveToFile(){
        BufferedWriter out = null;
        try {
            out = new BufferedWriter (new FileWriter("customers.txt"));

            for (ClsCustomer Client : theClients) {
                out.write(Client.outputCustomerDetails());
                out.write(Client.getCustomerAddress().outputAddress() + "]");
                //Using account object in client to access method in account object
                //Client.getCustomerAccount().saveToFile(out);
                out.write(System.getProperty("line.separator"));
            }
            out.write("\n");
            out.close();
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe2) {
                System.out.println("IO Problem: " + ioe2);
            }
        }
    }
    
    public void loadFromFile(String filename, int param) throws ParseException {
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
            theClients.clear();
            
            //I need to increment i by 4 each time, because one client is made of 4 entries
            for (int i = 0; i < words.size() - 12; i += 12) {
                
                //new SimpleDateFormat("dd/MM/yyyy").parse(DetailsArray[i+2]);
                //System.out.println(new SimpleDateFormat("dd/MM/yyyy").parse(DetailsArray[i+2]));
                
                //Date omg = new SimpleDateFormat("dd/MM/yyyy").parse(DetailsArray[i+2]);
                /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dateOB = sdf.parse(DetailsArray[i+2]);
                */
                
                ClsCustomer newCustomer = new ClsCustomer(
                        DetailsArray[i].replace("[", ""), 
                        DetailsArray[i+1], 
                        DetailsArray[i+2],
                        DetailsArray[i+3],
                        //Integer.parseInt(DetailsArray[i+4]),
                        
                        new ClsIAddress(
                                DetailsArray[i + 4],
                                DetailsArray[i + 5],
                                Integer.parseInt(DetailsArray[i + 6]),
                                DetailsArray[i + 7],
                                DetailsArray[i + 8],
                                DetailsArray[i + 9],
                                DetailsArray[i + 10],
                                DetailsArray[i + 11].replace("]", "")
                        )
                        /*,
                        new ClsAccount(
                                DetailsArray[i+12],
                                Integer.parseInt(DetailsArray[i+13]),
                                Double.parseDouble(DetailsArray[i+14]),
                                DetailsArray[i+15],
                                Double.parseDouble(DetailsArray[i+16].replace("]", ""))
                        )
                        */
                );
                theClients.add(newCustomer);
            }
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
        }
    }
    
    public boolean findCustomer(JTextArea src, ClsCustomer aCustomer) {
        boolean found = false;
        for (ClsCustomer Client : theClients) {
            if (Client.isTheSame(aCustomer)) {
                Client.displayCustomerDetails(src);
                Client.getCustomerAddress().display(src, 0);
                return found = true;
            }
        }
        if (!found) {
            return found = false;
        }
        // possible error
        return found;
    }
    
    // Code repetition below
    /*
    public ClsCustomer findCustomerinArray(String firstName, String surname, String DOB) {
        ClsCustomer aCustomer = new ClsCustomer();
        for (int i = 0; i < theClients.size(); i++) {
            if (    theClients.get(i).getCustomerDetails()[0].equals(firstName)  &&
                    theClients.get(i).getCustomerDetails()[1].equals(surname)    &&
                    theClients.get(i).getCustomerDetails()[2].equals(DOB)
            ){
                aCustomer = theClients.get(i);
            }
        }
        return aCustomer;
    }
    */
    // not needed now
    /*
    public void findCustomerAccount(JTextArea src, ClsCustomer aCustomer) {
        boolean found = false;

        for (ClsCustomer Client : theClients) {
            if (Client.isTheSame(aCustomer)) {
                //Client.getCustomerAccount().Display(src);
                found = true;
            }
        }
        if (!found) {
            src.setText("Customer not found.");
        }
    }
    */
}
