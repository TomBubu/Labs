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
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 *
 * @author ee16ttz
 */
public class ClsBranchList {
    private ArrayList <ClsBranch> theBranches = new ArrayList <>();
    private ClsBranchList SubDepartments;
    
    public ClsBranchList(){
        SubDepartments = null;
    }
    
    public void display(JTextArea src){
        for (ClsBranch Branch : theBranches) {
            Branch.displayBranchAddress(src); 
            Branch.displayBranchDetails(src);
        }
        System.out.println( theBranches.size() );
    }
    
    public boolean addBranch(ClsBranch newBranch){
        int old_size = theBranches.size();
        theBranches.add(newBranch);
        int new_size = theBranches.size();
        return new_size > old_size;
    }
            
    public boolean deleteBranch(String name){
        int old_size = theBranches.size();
        int new_size = old_size;

        for (int i = 0; i < theBranches.size(); i++) {
            if (theBranches.get(i).getBranchName().equals(name) ) {
                theBranches.remove(i);
                new_size--;
            }
        }
        return (new_size < old_size);
    }        
    
    public void saveToFile(){
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("branches.txt"));
            for (ClsBranch Branch : theBranches) {
                out.write("[");
                out.write(Branch.getBranchAddress());
                //branchName+", "+houseName+", "+houseNo+", "+street+", "+area+", "+postCode+", "+town+", "+country;
                out.write(", ");
                out.write(Branch.outputBranchDetails());
                //sortCode +", "+ workingHours;
                out.write("]");
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
    
    public void loadFromFile(String filename){
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
            //lebo to ides aj tak cele nacitavas a v tomto momente tam nemaju byt ziadne hodnoty (to sprav este pred tym for-om)
            theBranches.clear();
            
            //I need to increment i by 4 each time, because one client is made of 4 entries
            for (int i = 0; i < words.size() - 10; i += 10) {
                ClsBranch newBranch = new ClsBranch(
                        DetailsArray[i].replace("[", ""),       //branch name   1
                        DetailsArray[i+1],                      //house name    2
                        Integer.parseInt(DetailsArray[i+2]),    //house number  3
                        DetailsArray[i+3],                      //street        4
                        DetailsArray[i+4],                      //area          5
                        DetailsArray[i+5],                      //post code     6   
                        DetailsArray[i+6],                      //town          7
                        DetailsArray[i+7],                      //country       8
                        DetailsArray[i+8],                      //sort code     9
                        DetailsArray[i+9].replace("]", "")      //working hours 10
                       
                );
                theBranches.add(newBranch);
            }
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
        }
    }
    
    public void createDepartment(){
        if(SubDepartments == null){
            SubDepartments = new ClsBranchList();
        }
    }
    
    public ClsBranchList getSubDepartments(){
        return SubDepartments;
    }
   
    public ClsBranch findBranch(String name) {
        ClsBranch theBranch = new ClsBranch();
        for (ClsBranch Branch : SubDepartments.theBranches) {
            if (Branch.getBranchName().matches(name)) {
                //System.out.println(Branch.getBranchName());
                theBranch = Branch;
                //System.out.println(Branch.outputBranchDetails());
              }
        }
        return theBranch;
    }
    
    public void removeLineFromFile(String filename, String lineToRemove) {
        BufferedReader br;
        PrintWriter pw = null;
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
                    // Fix to be able to remove the .tmp file on Windows:
                    // https://stackoverflow.com/questions/991489/file-delete-returns-false-even-though-file-exists-file-canread-file-canw/21522963
                    System.gc();
                }
            }
            pw.close();
            br.close();

            //new stuff, delete if doesnt work
            /*
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            saveToSubdepartmentsFile(writer, filename, aSubBranch);
            writer.close();
            */
            
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
    
    public void saveToSubdepartmentsFile(String filename, ClsBranch aSubBranch) {    
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename, true));
            aSubBranch.saveToSubDepartmentsFile(writer);
            
            writer.newLine();
            writer.close();
        } catch (IOException ioe1) {
            System.out.println("IO Problem: " + ioe1);
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ioe2) {
                    System.out.println("IO Problem: " + ioe2);
                }
            }
        }
    }
    
    
}
