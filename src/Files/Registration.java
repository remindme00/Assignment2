/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Files;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author User
 */
public class Registration {
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;
    
    
    public Registration(){
       dbManager = new DBManager();
       conn = dbManager.getConnection();
    }
    
    
    //return result of user info that matches UserID
    public ResultSet getUser(int userID){
        ResultSet rs = null;
        try {
            String dataQuery = "SELECT FNAME, LNAME, AGE, EMAIL, PHONE FROM CUSTOMER WHERE USERID = "+userID+"";
            this.statement = conn.createStatement();
            rs = this.statement.executeQuery(dataQuery);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
        
        //the end
    }
    
    //upadte user info using the new input
    public void updateReg(int userID, String fName, String lName, String age, String email, String phNumber){
        int Age;
        try {
            Age = Integer.parseInt(age.trim());            
        }
        catch (NumberFormatException e) {
            Age = 0;
        }
        try {
            this.statement = conn.createStatement();
            this.statement.addBatch("UPDATE CUSTOMER SET FNAME = '"+fName+"', "
                    + "LNAME = '"+lName+"', "
                            + "AGE = "+Age+", "
                                    + "EMAIL = '"+email+"', "
                                            + "PHONE = '"+phNumber+"' "
                                                    + "WHERE USERID = "+userID+"");
            this.statement.executeBatch();
            this.statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        //the end
    }
    
    //get the last customer userID/max number used and add 1 to get new id
    public int getLastID(){
        ResultSet rs;
       
        int lastID = 0;
        try {
            rs = this.statement.executeQuery("SELECT MAX(USERID)"
                + "FROM CUSTOMER");
            if(rs.next()){
                lastID = (((Number)rs.getObject(1)).intValue()) + 1;
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lastID; 
        
        //the end
    }
    
    //add/create  new customer/user info 
    public void adduser(String fName, String lName, String age, String phNumber, String email){
        int Age;
        try {
            Age = Integer.parseInt(age.trim());
        }
        catch (NumberFormatException e) {
            Age = 0;
        }
        try {
            this.statement = conn.createStatement();
            this.statement.addBatch("INSERT INTO CUSTOMER (USERID, FNAME, LNAME, AGE, EMAIL, PHONE)"
                    + "VALUES("+getLastID()+", '"+fName+"', '"+lName+"', "+Age+", '"+email+"', '"+phNumber+"')");
            this.statement.executeBatch();
            this.statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        //the end
    }
    
    //check for error while the user is typing new value  and returns the error message
    public String checkInfo(String fName, String lName, String age, String phNumber, String email){
        String returnMessage = "";
        int Age;
        try {
            Age = Integer.parseInt(age.trim());
        }
        catch (NumberFormatException e) {
            Age = 0;
        }
        //First Name
        if((fName.matches("^.*[^a-zA-Z ].*$") || "".equalsIgnoreCase(fName) || "First Name".equalsIgnoreCase(fName)))
        {
            returnMessage = "invalid firstname, please type in alphabetic name eg.JOHN";
            return returnMessage;
        }
        //Last Name
        else if((lName.matches("^.*[^a-zA-Z ].*$") || "".equalsIgnoreCase(lName) || "Last Name".equalsIgnoreCase(lName)))
        {
            returnMessage = "invalid lastname, please type in alphabetic name eg.TOMMY";
            return returnMessage;
        }
        //Age
        
        else if((Age<18) || "Age".equalsIgnoreCase(age))
        {
            returnMessage = "must be 18 or older";
            return returnMessage;
        } 
        //Email
        else if((!email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$") 
                || "".equalsIgnoreCase(email) || "Email".equalsIgnoreCase(email)))
        {
            returnMessage = "invalid email, hint: example@gmail.com";
            return returnMessage;
        }
        //Phone Number   
        else if((phNumber.matches("^.*[^0-9 ].*$") || "".equalsIgnoreCase(phNumber) || "Phone Number".equalsIgnoreCase(phNumber)))
        {
            returnMessage = "invalid phone number, hint: 022385960";
            return returnMessage;
        }
        return returnMessage;
        
        //the end
    }
    
    //check for error while the user is typing new value  and returns boolean result
    public boolean check(String fName, String lName, String age, String phNumber, String email){
        int Age;
        try {
            Age = Integer.parseInt(age.trim());
        }
        catch (NumberFormatException e) {
            Age = 0;
        }
        //First Name
        if((fName.matches("^.*[^a-zA-Z ].*$") || "".equalsIgnoreCase(fName) || "First Name".equalsIgnoreCase(fName)))
        {
            return false;
        }
        //Last Name
        else if((lName.matches("^.*[^a-zA-Z ].*$") || "".equalsIgnoreCase(lName) || "Last Name".equalsIgnoreCase(lName)))
        {
            return false;
        }
        //Age
        
        else if((Age<18) || "Age".equalsIgnoreCase(age))
        {
            return false;
        } 
        //Email
        else if((!email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$") 
                || "".equalsIgnoreCase(email) || "Email".equalsIgnoreCase(email)))
        {
            return false;
        }
        //Phone Number   
        else if((phNumber.matches("^.*[^0-9 ].*$") || "".equalsIgnoreCase(phNumber) || "Phone Number".equalsIgnoreCase(phNumber)))
        {
            return false;
        }
        
        return true;
        
        //the end
    }
   
    
    //the end of the class
}
