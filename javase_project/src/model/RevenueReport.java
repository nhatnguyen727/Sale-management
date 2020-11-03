/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author stfu
 */
public class RevenueReport {

    private Date Dates;
    private String MonthsOfAYear;
    private float TotalBill;
    private float TotalFund;
    private float TotalProfit;

    public RevenueReport(Date Dates, float TotalBill, float TotalFund, float TotalProfit) {
        this.Dates = Dates;
        this.TotalBill = TotalBill;
        this.TotalFund = TotalFund;
        this.TotalProfit = TotalProfit;
    }

    public RevenueReport(String MonthsOfAYear, float TotalBill, float TotalFund, float TotalProfit) {
        this.MonthsOfAYear = MonthsOfAYear;
        this.TotalBill = TotalBill;
        this.TotalFund = TotalFund;
        this.TotalProfit = TotalProfit;
    }

    public Date getDates() {
        return Dates;
    }

    public void setDates(Date Dates) {
        this.Dates = Dates;
    }

    public String getMonthsOfAYear() {
        return MonthsOfAYear;
    }

    public void setMonthsOfAYear(String MonthsOfAYear) {
        this.MonthsOfAYear = MonthsOfAYear;
    }

    public float getTotalBill() {
        return TotalBill;
    }

    public void setTotalBill(float TotalBill) {
        this.TotalBill = TotalBill;
    }

    public float getTotalFund() {
        return TotalFund;
    }

    public void setTotalFund(float TotalFund) {
        this.TotalFund = TotalFund;
    }

    public float getTotalProfit() {
        return TotalProfit;
    }

    public void setTotalProfit(float TotalProfit) {
        this.TotalProfit = TotalProfit;
    }

}
