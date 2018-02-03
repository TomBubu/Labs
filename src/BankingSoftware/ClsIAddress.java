/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsIAddress {
    private String branchName;
    private String houseName;
    private Integer houseNo;
    private String street;
    private String area;
    private String postCode;
    private String town;
    private String country;
    
    // Declaration of constructor with parameters
    public ClsIAddress(String branchName, String houseName, Integer houseNo, String street, 
            String area, String postCode, String town, String country)
    {
        change(branchName, houseName, houseNo, street, area, postCode, town, country);
    }
    
    // Default constructor
    public ClsIAddress(){
        change(" "," ",0," "," "," "," "," ");
    }
    
    /*##########################################################################################*/
    /*####################################### Methods ##########################################*/
    /*##########################################################################################*/
    public void display(JTextArea src, int param){
        //can we introduce a param that will display branch if param==1, else it wont?
        //I will pass param 1 only in branch case, else I will pass 0 to this function.
        if(param == 1){
            src.append( "Branch Name: "  +   this.branchName  +   "\n" );
        }
        
        src.append(
                //"Branch Name: "  +   this.branchName  +   "\n" +
                "House name: "   +   this.houseName   +   "\n" + 
                "House number: " +   this.houseNo     +   "\n" + 
                "Street: "       +   this.street      +   "\n" + 
                "Area: "         +   this.area        +   "\n" + 
                "Post Code: "    +   this.postCode    +   "\n" + 
                "Town: "         +   this.town        +   "\n" + 
                "Country: "      +   this.country     +   "\n"
        );
    }
    
    public void edit(String name, String houseName, Integer houseNo,
        String street, String area, String postCode, String town, String country)
    {
        change(name, houseName, houseNo, street, area, postCode, town, country);
    }
    
    private void change(String name, String houseName, Integer houseNo,
        String street, String area, String postCode, String town, String country){
        this.branchName = name;
        this.houseName = houseName;
        this.houseNo = houseNo;
        this.street = street;
        this.area = area;
        this.postCode = postCode;
        this.town = town;
        this.country = country; 
    }
    
    public void loadFromFile(BufferedReader abufferedreader ){
        String[] AddressArray = new String[8];
        String line;
        try{

            for (int index = 0; index < 8; index++) {
                if ((line = abufferedreader.readLine()) != null) {
                    AddressArray[index] = line;
                }
            }
            change( AddressArray[0], 
                    AddressArray[1], 
                    Integer.parseInt(AddressArray[2]), 
                    AddressArray[3], AddressArray[4], 
                    AddressArray[5], AddressArray[6], 
                    AddressArray[7]);
        }catch(IOException ioe3){
            System.out.println("IO Problem: " + ioe3);
            ioe3.printStackTrace();
        }
       
    }

    //fix
    public void saveToFile(FileWriter awriter)
    {
        try{
            awriter.write(branchName + System.getProperty("line.separator"));
            awriter.write(houseName + System.getProperty("line.separator"));
            awriter.write(houseNo + System.getProperty("line.separator"));
            awriter.write(street + System.getProperty("line.separator") + area + System.getProperty("line.separator"));
            awriter.write(postCode + System.getProperty("line.separator"));
            awriter.write(town + System.getProperty("line.separator"));
            awriter.write(country + System.getProperty("line.separator"));
        }catch(IOException ioe){
            System.out.println("IO Problem: " + ioe);
        }
    }

    public String getName() {
        return this.branchName;
    }
    
    /*
    public String[] getBranchAddress(){
        String[] BranchAddress = new String[8];
        
        BranchAddress[0] = name;
        BranchAddress[1] = houseName;
        BranchAddress[2] = houseNo.toString();
        BranchAddress[3] = street;
        BranchAddress[4] = area;
        BranchAddress[5] = postCode;
        BranchAddress[6] = town;
        BranchAddress[7] = country;
        
        return BranchAddress;
    }
    */
    
    public String outputAddress(){
        return branchName+", "+houseName+", "+houseNo+", "+street+", "+area+", "+postCode+", "+town+", "+country;
    }

    public boolean isTheSame(ClsIAddress customerAddress) {
        if (branchName.equals(customerAddress.branchName)
                && houseName.equals(customerAddress.houseName)
                && houseNo.equals(customerAddress.houseNo)
                && street.equals(customerAddress.street)
                && area.equals(customerAddress.area)
                && postCode.equals(customerAddress.postCode)
                && town.equals(customerAddress.town)
                && country.equals(customerAddress.country)) {
            return true;
        } else {
            return false;
        }
    }
}