/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsBranch {
    private ClsIAddress theAddress;
    private ClsPerson theManager;
    private String theName;
    private String workingHours;
    private String sortCode;
    
    // Default constructor
    public ClsBranch(){
        theAddress = new ClsIAddress(" ", " ", 0, " ", " ", " ", " ", " ");
        theManager = new ClsPerson(" ", " ", " ", " ");
        workingHours = "9:00-17:00";
        sortCode = "00-00-00";
    }
    
    public ClsBranch(String name, String houseName, Integer houseNo, String street, String area, String postCode, String town, String country,
            String sortCode, String workingHours)
    { 
        theAddress = new ClsIAddress();
        
        editBranchAddress(name, houseName, houseNo, street, area, postCode, town, country);
        editBranchDetails(sortCode, workingHours);
    }
    
  
    /*####################################### Methods ##########################################*/
    public void displayBranchAddress(JTextArea src){
       theAddress.display(src, 1);
       //src.append("\n" + "Working hours :" + workingHours + "\n" + "Sort code: " + sortCode + "\n" + "Manager: " + Manager);
    }
   
    public void displayBranchDetails(JTextArea src){
        src.append(
            /*"Manager :" + theManager + "\n" +*/
            "Sort Code: "       + this.sortCode     + "\n" +
            "Working Hours: "   + this.workingHours + "\n\n\n");
    }
    
    public void editBranchDetails(/*ClsPerson strManager, */String sortCode, String workingHours){
        //theManager = strManager;
        this.sortCode = sortCode;
        this.workingHours = workingHours;
    }
    
    public void editBranchAddress(String name, String houseName, Integer houseNo,
            String street, String area, String postCode, String town, String country)
    {
         theAddress.edit(name, houseName, houseNo, street, area, postCode, town, country);
    }
    
    public void loadFromFile(String filename){
        String[] DetailsArray = new String[2];
        String line;
        BufferedReader in = null;
        try{
            in = new BufferedReader(new FileReader(filename));
            
            theAddress.loadFromFile(in);
            
            for (int index = 0; index < 2; index++) {
                if ((line = in.readLine()) != null) {
                    DetailsArray[index] = line;
                }
            }
            
            editBranchDetails(DetailsArray[1], DetailsArray[0]);
            
            in.close();
        } catch (IOException ioe1) {
            ioe1.printStackTrace();
            System.out.println("IO Problem: " + ioe1);
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe2) {
                ioe2.printStackTrace();
                System.out.println("IO Problem: " + ioe2);
            }
        }
    }

    public void saveToFile() {
        FileWriter out = null;
        try {
            out = new FileWriter("headBranch.txt");
            theAddress.saveToFile(out);
            out.write(sortCode + System.getProperty("line.separator"));
            out.write(workingHours + System.getProperty("line.separator"));
            out.close();
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
            ioe1.printStackTrace();
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ioe2) {
                ioe2.printStackTrace();
                System.out.println("IO Problem: " + ioe2);
            }
        }
    }
    
    public String branchDetailsOutput(){
        String branchDetails = (this.sortCode + ", " + this.workingHours);
        return branchDetails;
    }
    
    /*
    private String branchAddressOutput(){
        String branchAddress = (//get object anAddress here);
        return branchAddress;  
    }*/
    
    public void saveToSubDepartmentsFile(BufferedWriter srcWriter) {
        try {
            srcWriter.write(theAddress.outputAddress());
            //branchName+", "+houseName+", "+houseNo+", "+street+", "+area+", "+postCode+", "+town+", "+country;
            srcWriter.write(", ");
            srcWriter.write(branchDetailsOutput());
            //sortCode + ", " + workingHours
            srcWriter.write(System.getProperty("line.separator"));
            srcWriter.write("EmptyLine");
            System.gc();
        } catch (IOException ioe) {
            System.out.println("IO Problem: " + ioe);
        }
    }
    
    /*
    public boolean IsHeadOffice(){
        return true;
    }
    */
    
    /*
    public double getOverallBalance(){
        double OverallBalance = 0.00;
        return OverallBalance;
    }
    */
    
    public String getBranchName() {
        return theAddress.getName();
    }

    public String outputBranchDetails(){
        return this.sortCode +", "+ this.workingHours;
    }
    
    public String getBranchAddress(){
        String branchAddress = theAddress.outputAddress();
        return branchAddress;
    }
    
}
