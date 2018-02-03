/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JComboBox;

/**
 *
 * @author ee16ttz
 */
public class ClsUser {
    private String name;
    private String role;
    private String password;
    private String filename;
    
    // Default constructor
    public ClsUser(){
        name = " ";
        role = "Bank Employee";
        password = " ";
        filename = "login.txt";
    }
    
    // Input validation method
    public boolean areLoginInputsCorrect(JComboBox src, String newName, String newPassword) {
        boolean isCorrect;
        
        isCorrect = (newName.length() != 0
                && newPassword.length() != 0
                && !newName.contains("#")
                && !newPassword.contains("#")
                );
        return isCorrect;
    }
    
    public boolean isRegistered(String newName, String newPassword, String newRole){
        boolean isRegistered;
        name = newName;
        password = newPassword;
        role = newRole;
        
        FileWriter writer;

        try {
            writer = new FileWriter(filename, true);
            writer.write(name + " " + password + " " + role + System.getProperty("line.separator") + "##" + System.getProperty("line.separator"));
            isRegistered = true;
            writer.flush();
            writer.close();
            writer = null;
        } catch (IOException ioe) {
            isRegistered = false;
        }
        return isRegistered;
    }
    
    public boolean isUser(String newName, String newPassword, String newRole){
        name = newName;
        password = newPassword;
        role = newRole;
        
        boolean isFound = false;
        FileReader reader;
        
        try{
            String record = null;
            reader = new FileReader(filename);
            BufferedReader bin = new BufferedReader(reader);
            record = new String();
            while ( (record = bin.readLine() ) != null){
                if (
                        // For inline file:
                        (name+" "+password+" "+role).contentEquals(record))
                    
                        // For paragraphed file: Doesnt work
                        /*(
                        name + System.getProperty("line.separator") + 
                        password + System.getProperty("line.separator") +
                        role + System.getProperty("line.separator")
                        ).contentEquals(record))*/
                {
                    isFound = true;
                }
            }
            bin.close();
            bin = null;
        }catch (IOException ioe){
            isFound = false;
        }
        return isFound;
    }
    
    /*
    public boolean isManager(String myFile, String strname, String strpassword, String newRole){
        name = strname;
        password = strpassword;
        role = newRole;
        boolean isManager = false;
                
        if ( isUser(myFile, strname, strpassword, newRole) == true) {
            
            if (newRole.equals("Bank Manager")) {
                isManager = true;
            } 
            else {
                if (!newRole.equals("Bank Manager")) {
                    //echo "You have insufficient permissions."
                    isManager = false;
                }
            }
        }
        else{
            //echo "You are not a user."
            isManager = false;
        }
        
        return isManager;
    }
    */
    
    public String getRole(){
        return this.role;
    }
    

}
