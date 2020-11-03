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
public class ReceiptNote {
    private int RepID;
    private int EmpID;
    private float TotalMoney;
    private Date Dates;
    private int ProviderID;

    public ReceiptNote() {
    }

    public ReceiptNote(int RepID, int EmpID, float TotalMoney, Date Dates, int ProviderID) {
        this.RepID = RepID;
        this.EmpID = EmpID;
        this.TotalMoney = TotalMoney;
        this.Dates = Dates;
        this.ProviderID = ProviderID;
    }

    public ReceiptNote(float TotalMoney, Date Dates) {
        this.TotalMoney = TotalMoney;
        this.Dates = Dates;
    }

    public int getRepID() {
        return RepID;
    }

    public void setRepID(int RepID) {
        this.RepID = RepID;
    }

    public int getEmpID() {
        return EmpID;
    }

    public void setEmpID(int EmpID) {
        this.EmpID = EmpID;
    }

    public float getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(float TotalMoney) {
        this.TotalMoney = TotalMoney;
    }

    public Date getDates() {
        return Dates;
    }

    public void setDates(Date Dates) {
        this.Dates = Dates;
    }

    public int getProviderID() {
        return ProviderID;
    }

    public void setProviderID(int ProviderID) {
        this.ProviderID = ProviderID;
    }

}
