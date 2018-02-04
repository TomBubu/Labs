/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
          this.homeAddress.isTheSame(aCustomer.getCustomerAddress()) //&&
          //CustomerAccount.isTheSame(aCustomer.getCustomerAccount())
          ){
            return true;
        }
        else{
            return false;
        }
    }
    
    // #Methods for account opearations below# //
    public void createAccount(ClsAccount src, DefaultListModel srcModel, String type){
        ownedAccounts = new ArrayList<>();
        ownedAccounts.add(src);
        srcModel.addElement("Type: " + type + src.outputDetails());
        this.numberOfAccounts++;
    }
  
    public ClsAccount returnSpecificAcc(String sortCode, int accountNo) {
        
        // Testing
        System.out.println("Size: " + ownedAccounts.size());
        for(int i=0; i < ownedAccounts.size(); i++){
            System.out.println(ownedAccounts.get(i).outputDetails());
        }
       
        for (int i = 0; i < ownedAccounts.size(); i++) {
            if ((ownedAccounts.get(i).sortCode.equals(sortCode)) && (ownedAccounts.get(i).accountNo == accountNo)) {
                return ownedAccounts.get(i);
            }
        }
        return null;
    }
    
    /*
    public void create(ClsAccount anAccount){
        this.CustomerAccount = anAccount;
        anAccount.setCustomer(this);
    }
*/
    
    public void saveToClientFile(String accountType, String filename, ClsAccount account){
        BufferedWriter customerWriter = null;
        int numOfAccounts = ownedAccounts.size();
        try{
            //customerWriter = new BufferedWriter(new FileWriter("clients_"+ this.FirstName+this.Surname+"_File.txt", true));
            customerWriter = new BufferedWriter(new FileWriter(filename, true));
            
            customerWriter.write(numOfAccounts + ", ");                         // Param  1
            customerWriter.write(FirstName+", "+Surname+", "+DOB + ", ");       // Params 2, 3, 4
            for(int i = 0; i< numOfAccounts; i++){                              //
                customerWriter.write(account.outputToFile());                   // Params 5,6,7,8,9,10,11 per 1 account
            }
            customerWriter.write("EmptyLine");                                  // Param 12 ; Not to be read but must be accounted for
            
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
    
    public void loadFromFile(int param){
        //customer logged in
        if(param==1){
            //("clients_"+ this.FirstName+this.Surname+"_File.txt");
        }
        //manager logged in
        else if(param==2){
            /*for(){
                probably bad idea.
            }*/
        }
        else{
            //to be coded
        }
    }
   
    // Probably should be done by nicer way
    public boolean checkSize(){
        if(this.ownedAccounts != null){
            return true;
        }
        else return false;
    }
    
}