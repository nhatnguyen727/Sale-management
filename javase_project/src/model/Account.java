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
public class Account {
    private int AccID;
    private String Username;
    private String Password;
    private int EmpID;
    private int PosID;

    public Account() {
    }

    public Account(int AccID, String Username, String Password, int EmpID, int PosID) {
        this.AccID = AccID;
        this.Username = Username;
        this.Password = Password;
        this.EmpID = EmpID;
        this.PosID = PosID;
    }

    public int getAccID() {
        return AccID;
    }

    public void setAccID(int AccID) {
        this.AccID = AccID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getEmpID() {
        return EmpID;
    }

    public void setEmpID(int EmpID) {
        this.EmpID = EmpID;
    }

    public int getPosID() {
        return PosID;
    }

    public void setPosID(int PosID) {
        this.PosID = PosID;
    }
    
    
}
