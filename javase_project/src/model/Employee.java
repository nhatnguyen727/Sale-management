/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author TechCare
 */
public class Employee {

    private int EmpID;
    private String EmpName;
    Date DoB;
    private String Gender;
    private String EmpAdress;
    private String TellNo;
    private String Email;
    private double luong;

    public Employee() {
    }

    public Employee(int EmpID, String EmpName, Date DoB, String Gender, String EmpAdress, String TellNo, String Email, double luong) {
        this.EmpID = EmpID;
        this.EmpName = EmpName;
        this.DoB = DoB;
        this.Gender = Gender;
        this.EmpAdress = EmpAdress;
        this.TellNo = TellNo;
        this.Email = Email;
        this.luong = luong;
    }

    public int getEmpID() {
        return EmpID;
    }

    public void setEmpID(int EmpID) {
        this.EmpID = EmpID;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String EmpName) {
        this.EmpName = EmpName;
    }

    public Date getDoB() {
        return DoB;
    }

    public void setDoB(Date DoB) {
        this.DoB = DoB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getEmpAdress() {
        return EmpAdress;
    }

    public void setEmpAdress(String EmpAdress) {
        this.EmpAdress = EmpAdress;
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

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

}
