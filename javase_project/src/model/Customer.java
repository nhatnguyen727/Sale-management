/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author TechCare
 */
public class Customer{
    private int CustID;
    private String CustName;
    private String Gender;
    private String CustAddress;
    private String TellNo;
    private String Email;

    public Customer() {
    }

    public Customer(int CustID, String CustName, String Gender, String CustAddress, String TellNo, String Email) {
        this.CustID = CustID;
        this.CustName = CustName;
        this.Gender = Gender;
        this.CustAddress = CustAddress;
        this.TellNo = TellNo;
        this.Email = Email;
    }

    public int getCustID() {
        return CustID;
    }

    public void setCustID(int CustID) {
        this.CustID = CustID;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String CustName) {
        this.CustName = CustName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String CustAddress) {
        this.CustAddress = CustAddress;
    }

    public String getTellNo() {
        return TellNo;
    }

    public void setTellNo(String TellNo) {
        this.TellNo = TellNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    
}
