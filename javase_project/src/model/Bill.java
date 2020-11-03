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
public class Bill {
    private int BillID;
    private int EmpID;
    private double TotalMoney;
    private Date Dates;
    private int CustID;

    public Bill() {
    }

    public Bill(int BillID, int EmpID, double TotalMoney, Date Dates, int CustID) {
        this.BillID = BillID;
        this.EmpID = EmpID;
        this.TotalMoney = TotalMoney;
        this.Dates = Dates;
        this.CustID = CustID;
    }

    public Bill(double TotalMoney, Date Dates) {
        this.TotalMoney = TotalMoney;
        this.Dates = Dates;
    }

    public int getBillID() {
        return BillID;
    }

    public void setBillID(int BillID) {
        this.BillID = BillID;
    }

    public int getEmpID() {
        return EmpID;
    }

    public void setEmpID(int EmpID) {
        this.EmpID = EmpID;
    }

    public double getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(double TotalMoney) {
        this.TotalMoney = TotalMoney;
    }

    public Date getDates() {
        return Dates;
    }

    public void setDates(Date Dates) {
        this.Dates = Dates;
    }

    public int getCustID() {
        return CustID;
    }

    public void setCustID(int CustID) {
        this.CustID = CustID;
    }
    
    
    
}
