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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsCustomer {
    private ClsPerson thePerson;
    private ClsIAddress homeAddress;
    private ClsCustomer accountCoHolder;
    //private ClsAccount CustomerAccount; // used previously in lab 6 or 7
    // ArrayList of object instances of class Account
    private ArrayList<ClsAccount> ownedAccounts;
        
    private String FirstName;
    private String Surname;
    private Date DOB;
    private Date CustomerSince;

    // Default constructor
    public ClsCustomer(){
        //thePerson = new ClsPerson();
        homeAddress = new ClsIAddress();
        ownedAccounts = new ArrayList<>();
        FirstName = " ";
        Surname = " ";
        DOB = new Date(01/01/1900);
        CustomerSince = new Date(01/01/1900);
    }
    
    public ClsCustomer(String firstName, String surname, Date DOB, Date customerSince, ClsIAddress theAddressObject){
        editCustomerDetails(firstName, surname, DOB, customerSince, theAddressObject);
    }
    
    public void displayCustomerAddress(JTextArea src){
        homeAddress.display(src, 0);
    }
    
    public void displayCustomerDetails(JTextArea src){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        src.append(
                "First name: "      + this.FirstName        + "\n"  +
                "Surname: "         + this.Surname          + "\n"  +
                "Date of Birth: "   + sdf.format(this.DOB)              + "\n"  +
                "Customer since: "  + sdf.format(this.CustomerSince)    + "\n"  
        );
    }
    
    public void editCustomerDetails(String firstName, String surname, Date DOB, Date customerSince, ClsIAddress addressObject){
        this.FirstName = firstName;
        this.Surname = surname;
        this.DOB = DOB;
        this.CustomerSince = customerSince;
        this.homeAddress = addressObject;
    }
    
    /*
    public void editCustomerDetails(ClsPerson aPerson, ClsIAddress addressObject){
        this.thePerson = aPerson;
        this.homeAddress = addressObject;
        or
        editCustomerDetails(aPerson, theAddressObject);
    }*/
    
    public void editCustomerAddress(String name, String houseName, Integer houseNo,
            String street, String area, String postCode, String town, String country)
    {
        homeAddress.edit(name, houseName, houseNo, street, area, postCode, town, country);
    }
    // Source https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
    public boolean checkDOBFormat(String DOB) {
        if (DOB == null || !DOB.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        try {
            df.parse(DOB);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }
    
    public String[] getCustomerDetails(){
        String[] CustomerDetails = new String[5];
        CustomerDetails[0] = this.FirstName;
        CustomerDetails[1] = this.Surname;
        
        CustomerDetails[2] = this.DOB.toString();
        System.out.println(CustomerDetails[2]);
        
        CustomerDetails[3] = this.CustomerSince.toString();
        System.out.println(CustomerDetails[3]);
        return CustomerDetails;
    }
    
    
    
    public String outputCustomerDetails(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "["+this.FirstName+", "+this.Surname+", "+sdf.format(this.DOB)+", "+sdf.format(this.CustomerSince)+", ";
    }
    
    public ClsIAddress getCustomerAddress(){
        return this.homeAddress;
    }
    
    /*
    public ClsAccount getCustomerAccount(){
        return this.CustomerAccount;
    }
    */
    
    public boolean isTheSame(ClsCustomer aCustomer){
        //System.out.print("class customer dob: " + DOB + "\n");
        //System.out.print("objects dob passed from gui: " + aCustomer.DOB + "\n");
        //if(this.DOB.equals(aCustomer.DOB) == true){System.out.println("all good");}
        
        if(
          this.FirstName.equals(aCustomer.FirstName) &&
          this.Surname.equals(aCustomer.Surname) &&
          this.DOB.equals(aCustomer.DOB) &&
          this.CustomerSince.equals(aCustomer.CustomerSince) &&
          this.homeAddress.isTheSame(aCustomer.getCustomerAddress())
          //&& CustomerAccount.isTheSame(aCustomer.getCustomerAccount())
          ){
            return true;
        }
        else{
            return false;
        }
    }
    
    // #Methods for account opearations below# //
    public void createAccountList(){
        if(ownedAccounts == null){
            ownedAccounts = new ArrayList<>();
        }
    }
        
    public void createAccount(ClsAccount src/*, DefaultListModel srcModel*/){
            ownedAccounts.add(src);
            //srcModel.addElement("Type: " + src.type + src.outputDetails());
    }
    
    public void populateJListFromArrayList(DefaultListModel srcModel) {
        srcModel.clear();
        for (ClsAccount account : ownedAccounts) {
            srcModel.addElement("Type: " + account.type + account.outputDetails());
        }
    }
    
    public boolean checkSize(){
        if(this.ownedAccounts != null){
            return true;
        }
        else return false;
    }
  
    public ClsAccount returnSpecificAcc(String sortCode, int accountNo) {
          for (int i = 0; i < ownedAccounts.size(); i++) {
            if ((ownedAccounts.get(i).sortCode.equals(sortCode)) && (ownedAccounts.get(i).accountNo == accountNo)) {
                return ownedAccounts.get(i);
            }
        }
        return null;
    }
    
    public void saveToClientFile(String filename, ClsAccount account){
        BufferedWriter customerWriter = null;
        //int numOfAccounts = ownedAccounts.size();
        try{
            customerWriter = new BufferedWriter(new FileWriter(filename, true));
            //customerWriter.write(numOfAccounts);                                // Param 1
            //customerWriter.write(System.getProperty("line.separator"));
            //customerWriter.write(accountType);                                  // Param 2
            //customerWriter.write(accountType/* + ", "*/);
            //customerWriter.write(System.getProperty("line.separator"));
                //customerWriter.write(FirstName+", "+Surname+", "+DOB);     // Params 3, 4, 5  needed because in loading I need to find customer object by these and pass it to account constructor 
                //customerWriter.write(System.getProperty("line.separator"));
            
            if (account instanceof ClsCurrentAccount) {
                account.saveToFile(customerWriter);                       // Params 5,6,7,8,9,10,11,12 per 1 account + 13,14,15,16
            } else if (account instanceof ClsISAAccount) {
                account.saveToFile(customerWriter);                       // Params 6,7,8,9,10,11,12 per 1 account + 13,14
            } else if (account instanceof ClsSavingsAccount) {
                account.saveToFile(customerWriter);                       // Params 6,7,8,9,10,11,12 per 1 account + 13
            } 
            customerWriter.write(System.getProperty("line.separator"));
            customerWriter.write("EmptyLine");                                  // Param 13 ; Not to be read but must be accounted for
            customerWriter.newLine();
            System.gc();
            
            customerWriter.flush();
            customerWriter.close();
        } catch (IOException ioe1){
            System.out.println("IO Problem: " + ioe1);
            if (customerWriter != null){
                try{
                    customerWriter.close();
                } catch (IOException ioe2){
                    System.out.println("IO Problem: " + ioe2);
                }
            }
        }
    }
    
    /*Code repetition below*/
    public void loadCAsFromFile(String filename, ClsCustomer aCustomer) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                for (String word : line.split(", ")) {
                    words.add(word);
                }
            }
            String[] DetailsArray = words.toArray(new String[words.size()]);
            
            if(ownedAccounts!=null){
                ownedAccounts.clear();
            }
            
            for (int i = 0; i < words.size() - 11; i += 11) {
                ClsCurrentAccount newAccount = new ClsCurrentAccount(
                        DetailsArray[i],
                        DetailsArray[i+1], //sort code         2
                        Integer.parseInt(DetailsArray[i + 2]), //accountNo         3
                        Double.parseDouble(DetailsArray[i +3]), //balance           4
                        DetailsArray[i + 4], //nameOfBank        5
                        Double.parseDouble(DetailsArray[i + 5]), //rate              6
                        Integer.parseInt(DetailsArray[i + 6]), //transactions      7 
                        aCustomer, //ClsCustomer accountHolder     8
                        DetailsArray[i + 7], //conditions        9
                        Double.parseDouble(DetailsArray[i + 8]), //availableBalance  10
                        Double.parseDouble(DetailsArray[i + 9]), //overdraftLimit    11
                        Double.parseDouble(DetailsArray[i + 10]) //fee               12
                );
                ownedAccounts.add(newAccount);
            }
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
        }
    }
    
    public void loadISAsFromFile(String filename, ClsCustomer aCustomer) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                for (String word : line.split(", ")) {
                    words.add(word);
                }
            }
            String[] DetailsArray = words.toArray(new String[words.size()]);
            //ownedAccounts.clear();
            for (int i = 0; i <= words.size() - 9; i+=9) {// or 8?
                ClsISAAccount newAccount = new ClsISAAccount(
                        DetailsArray[i],    // type 1
                        DetailsArray[i+1], //sort code         2
                        Integer.parseInt(DetailsArray[i + 2]), //accountNo         3
                        Double.parseDouble(DetailsArray[i + 3]), //balance          4
                        DetailsArray[i + 4], //nameOfBank        5
                        Double.parseDouble(DetailsArray[i + 5]), //rate              6
                        Integer.parseInt(DetailsArray[i + 6]), //transactions      7  
                        aCustomer, //ClsCustomer accountHolder     8
                        Double.parseDouble(DetailsArray[i + 7]), //maxDepositPerYear 9
                        Double.parseDouble(DetailsArray[i + 8]) //depositedThisYear 10
                        
                );
                ownedAccounts.add(newAccount);
            }
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
        }
    }

    public void loadSAsFromFile(String filename, ClsCustomer aCustomer) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                for (String word : line.split(", ")) {
                    words.add(word);
                }
            }
            String[] DetailsArray = words.toArray(new String[words.size()]);
            //ownedAccounts.clear();
            for (int i = 0; i <= words.size() - 8; i += 8) {
                ClsSavingsAccount newAccount = new ClsSavingsAccount(
                        DetailsArray[i], //type 1
                        DetailsArray[i+1], //sort code         2
                        Integer.parseInt(DetailsArray[i + 2]), //accountNo         3
                        Double.parseDouble(DetailsArray[i + 3]), //balance           4
                        DetailsArray[i + 4], //nameOfBank        5
                        Double.parseDouble(DetailsArray[i + 5]), //rate              6
                        Integer.parseInt(DetailsArray[i + 6]), //transactions      7 
                        aCustomer, //ClsCustomer accountHolder     8
                        Double.parseDouble(DetailsArray[i + 7]) //withdrawLimit     9
                );
                ownedAccounts.add(newAccount);
                //Testing
                System.out.println(ownedAccounts.size());
            }
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
        }
    }
}