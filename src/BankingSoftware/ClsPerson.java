/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankingSoftware;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsPerson {
    private ClsIAddress personAddress;
    //private clsCustomerList TheCustomer;
    /*
        Customer IS A Person!
        so Customer will inherit Person 's methods!
    */
    
    private String firstName;
    private String surname;
    private Date DOB;
    private Date customerSince;
    
    /* Conversion of Date DOB to String DOBString
    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
    String DOBString = formatter.format(DOB);
    */

    // Default constructor
    public ClsPerson(){
        personAddress = new ClsIAddress();
        firstName = " ";
        surname = " ";
        DOB = new Date(01/01/1900);
        customerSince = new Date(01/01/1900);
    }
    
    public ClsPerson(String name, String surname, Date DOB, Date customerSince){
        editPersonDetails(name, surname, DOB, customerSince);
    }
    
    public void displayPersonAddress(JTextArea src){
        personAddress.display(src, 0);
    }
    
    public void displayPersonDetails(JTextArea src){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        src.setText(
                "First name: "      + this.firstName        + "\n"  +
                "Surname: "         + this.surname          + "\n"  +
                "Date of Birth: "   + sdf.format(this.DOB)  + "\n"  +
                "Customer since: "  + sdf.format(this.customerSince)
        );
    }
    
    public void editPersonDetails(String name, String surname, Date DOB, Date customerSince){
        this.firstName = name;
        this.surname = surname;
        this.DOB = DOB;
        this.customerSince = customerSince;
    }
    
    public void editPersonAddress(String name, String houseName, Integer houseNo,
            String street, String area, String postcode, String town, String country)
    {
        personAddress.edit(name, houseName, houseNo, street, area, postcode, town, country);
    }
 
}
                    


