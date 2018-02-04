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
    //private Date DOB;
    private String CustomerSince;
    private String DOB;
    private int numberOfAccounts; // can use size of ownedAccounts
    
    /* Conversion of Date DOB to String DOBString
    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
    String DOBString = formatter.format(DOB);
    */

    // Default constructor
    public ClsCustomer(){
        //thePerson = new ClsPerson();
        homeAddress = new ClsIAddress();
        ownedAccounts = new ArrayList<>();

        FirstName = " ";
        Surname = " ";
        //DOB = new Date(01/01/1900);
        //DOB = new SimpleDateFormat("01/01/1900");
        CustomerSince = "01/01/1900";
        DOB = "01/01/1900";
        numberOfAccounts = 0;
    }
    
    public ClsCustomer(String firstName, String surname, String DOB, String customerSince, ClsIAddress theAddressObject){
        editCustomerDetails(firstName, surname, DOB, customerSince, theAddressObject);
    }
   
    /*
    public ClsCustomer(String firstName, String surname, String DOB, String customerSince, ClsIAddress theAddressObject//, ClsAccount theAccountObject){
        editCustomerDetails(firstName, surname, DOB, customerSince, theAddressObject//, theAccountObject
        );
    }
    */
    
    //Fix this StringToDate
    /*
    public String StringToDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DOB = df.format(new Date());
        return DOB;
    }
    */
    
    public void displayCustomerAddress(JTextArea src){
        homeAddress.display(src, 0);
    }
    
    public void displayCustomerDetails(JTextArea src){
        src.append(
                "First name: "      + this.FirstName        + "\n"  +
                "Surname: "         + this.Surname          + "\n"  +
                "Date of Birth: "   + this.DOB              + "\n"  +
                "Customer since: "  + this.CustomerSince    + "\n"  
        );
    }
    
    public void editCustomerDetails(String firstName, String surname, String strDOB, String customerSince, ClsIAddress addressObject){
        this.FirstName = firstName;
        this.Surname = surname;
        this.DOB = strDOB;
        this.CustomerSince = customerSince;
        
        this.homeAddress = addressObject;
        //this.CustomerAccount = theAccountObject;
        //this.ownedAccounts = theAccountObject;

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
        CustomerDetails[2] = this.DOB/*.toString()*/;
        CustomerDetails[3] = this.CustomerSince;
        return CustomerDetails;
    }
    
    public String outputCustomerDetails(){
        return "["+this.FirstName+", "+this.Surname+", "+this.DOB+", "+this.CustomerSince+", ";
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
        
    public void createAccount(ClsAccount src, DefaultListModel srcModel, String type){
            ownedAccounts.add(src);
            srcModel.addElement("Type: " + type + src.outputDetails());
            this.numberOfAccounts++;
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
    
    public void saveToClientFile(String accountType, String filename, ClsAccount account){
        BufferedWriter customerWriter = null;
        int numOfAccounts = ownedAccounts.size();
        try{
            customerWriter = new BufferedWriter(new FileWriter(filename, true));
            //customerWriter.write(numOfAccounts);                                // Param 1
            //customerWriter.write(System.getProperty("line.separator"));
            //customerWriter.write(accountType);                                  // Param 2
            customerWriter.write(accountType/* + ", "*/);
            customerWriter.write(System.getProperty("line.separator"));
                //customerWriter.write(FirstName+", "+Surname+", "+DOB);     // Params 3, 4, 5  needed because in loading I need to find customer object by these and pass it to account constructor 
                //customerWriter.write(System.getProperty("line.separator"));
            
            if (account instanceof ClsCurrentAccount) {
                account.saveToFile(customerWriter);                       // Params 6,7,8,9,10,11,12 per 1 account + 13,14,15,16
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
    
    public void loadFromFile(ClsCustomer aCustomer, int param){
        //customer logged in
        if(param==1){
            // This method is automatically called from search from client, so when clients logs in, he/she does not
            // have an access to this method. Therefore, this method also has to called from... login button perhaps?
        }

        //manager logged in
        else if(param==2){
            //Filename has format of: "Client_"+theCustomer.getCustomerDetails()[0]+theCustomer.getCustomerDetails()[1]+".txt"
            String filename = "Client_"+aCustomer.FirstName + aCustomer.Surname+".txt";
            ArrayList<String> words = new ArrayList<>();
            
            try {
                ownedAccounts.clear();
                Scanner in = new Scanner(new File(filename));
                
                while(in.hasNextLine()) {
                    String line = in.nextLine();
                    for (String word : line.split(", ")) {
                        words.add(word);
                    }
                }
                String[] DetailsArray = words.toArray(new String[words.size()]);

                    String accountType = in.nextLine();
                    System.out.print(accountType);
                    
                    if(accountType.equals("Current Account")){
                        for(int i = 0; i<=words.size()-10 ;i+=10){
                            ClsCurrentAccount newAccount = new ClsCurrentAccount(
                                DetailsArray[i],                            //sort code         1
                                Integer.parseInt(DetailsArray[i + 1]),      //accountNo         2
                                Double.parseDouble(DetailsArray[i + 2]),    //balance           3
                                DetailsArray[i + 3],                        //nameOfBank        4
                                Double.parseDouble(DetailsArray[i + 4]),    //rate              5
                                Integer.parseInt(DetailsArray[i + 5]),      //transactions      6   
                                aCustomer,                                  //ClsCustomer accountHolder     7
                                DetailsArray[i + 6],                        //conditions        8
                                Double.parseDouble(DetailsArray[i + 7]),    //availableBalance  9
                                Double.parseDouble(DetailsArray[i + 8]),    //overdraftLimit    10
                                Double.parseDouble(DetailsArray[i + 9])     //fee               11
                            );
                            ownedAccounts.add(newAccount);
                        }
                    }
                    else if(accountType.equals("ISA Account")){
                        for(int i = 0; i<=words.size()-8 ;i++){
                            ClsISAAccount newAccount= new ClsISAAccount(
                                DetailsArray[i],                            //sort code         1
                                Integer.parseInt(DetailsArray[i + 1]),      //accountNo         2
                                Double.parseDouble(DetailsArray[i + 2]),    //balance           3
                                DetailsArray[i + 3],                        //nameOfBank        4
                                Double.parseDouble(DetailsArray[i + 4]),    //rate              5
                                Integer.parseInt(DetailsArray[i + 5]),      //transactions      6   
                                aCustomer,                                  //ClsCustomer accountHolder     7
                                Double.parseDouble(DetailsArray[i + 6]),    //maxDepositPerYear 8
                                Double.parseDouble(DetailsArray[i + 7])     //depositedThisYear 9
                                    
                            );
                            ownedAccounts.add(newAccount);
                        }
                    }
                    else if(accountType.equals("Saving Account")){
                        for(int i = 0; i<=words.size()-9 ;i+=9){
                            ClsSavingsAccount newAccount = new ClsSavingsAccount(
                                DetailsArray[i],                            //sort code         1
                                Integer.parseInt(DetailsArray[i + 1]),      //accountNo         2
                                Double.parseDouble(DetailsArray[i + 2]),    //balance           3
                                DetailsArray[i + 3],                        //nameOfBank        4
                                Double.parseDouble(DetailsArray[i + 4]),    //rate              5
                                Integer.parseInt(DetailsArray[i + 5]),      //transactions      6   
                                aCustomer,                                  //ClsCustomer accountHolder     7
                                Double.parseDouble(DetailsArray[i + 6])     //withdrawLimit     8
                            );
                            ownedAccounts.add(newAccount);
                        }
                    }
            } catch (IOException ioe1) {
                System.out.println("IO Problem: " + ioe1);
            }
            
            
            
            
            
            
            
            
        }
        else{
            
        }
        
        /*
            currentAcc = new ClsCurrentAccount( sortCode, accountNo, 0, bankName, 1.2, 0,    theCustomer,   conditions, 0, 100.00, 25.00);
            ISAAcc = new ClsISAAccount(         sortCode, accountNo, 0, bankName, 1.2, 0,    theCustomer,   3250, 0);
            savingsAcc = new ClsSavingsAccount( sortCode, accountNo, 0, bankName, 1.2, 0,    theCustomer,   200);
        */
        
    }
}