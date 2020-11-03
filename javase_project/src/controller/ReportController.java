/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ProductReport;
import model.RevenueReport;
import view.Message;

/**
 *
 * @author stfu
 */
public class ReportController {

    public static List<ProductReport> GetListProductRPByDay(Date dateFrom, Date dateTo) {
        ArrayList<ProductReport> list = null;
        try (Connection connection = connectDB.connectSQLServer()) {
            list = new ArrayList<>();
            String sql = "{call productReportByDates(?,?)}";
            try (CallableStatement cstmt = connection.prepareCall(sql)) {
                cstmt.setDate(1, dateFrom);
                cstmt.setDate(2, dateTo);
                try (ResultSet rs = cstmt.executeQuery()) {
                    while (rs.next()) {
                        ProductReport pr = new ProductReport(rs.getString("ProductName"), rs.getInt("ProductSoldInSpecificDate"));
                        list.add(pr);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (list == null) {
            new Message("Không tìm thấy hóa đơn nào từ ngày " + dateFrom + " đến ngày " + dateTo, 2, 6).setVisible(true);
        }
        return list;
    }

    public static Map<String, Integer> getTopListProductSold(Date dateFrom, Date dateTo) {
        Map<String, Integer> fullListMapDescBySold = new LinkedHashMap();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "{call productReportByDates(?,?)}";
            try (CallableStatement cstmt = connection.prepareCall(sql)) {
                cstmt.setDate(1, dateFrom);
                cstmt.setDate(2, dateTo);
                try (ResultSet rs = cstmt.executeQuery()) {
                    while (rs.next()) {
                        ProductReport pr = new ProductReport(rs.getString("ProductName"), rs.getInt("ProductSoldInSpecificDate"));
                        fullListMapDescBySold.put(pr.getProductName(), pr.getProductSold());
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fullListMapDescBySold;
    }

    public static List<RevenueReport> GetListRevenueRPByDay(Date dateFrom, Date dateTo) {
        ArrayList<RevenueReport> list = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "{call revenueReportByDates(?,?)}";
            try (CallableStatement cstmt = connection.prepareCall(sql)) {
                cstmt.setDate(1, dateFrom);
                cstmt.setDate(2, dateTo);
                try (ResultSet rs = cstmt.executeQuery()) {
                    while (rs.next()) {
                        RevenueReport rev = new RevenueReport(rs.getDate("Dates"), rs.getInt("totalBill"), rs.getInt("totalFundPerBill"), rs.getInt("totalProfit"));
                        list.add(rev);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public static List<RevenueReport> GetListRevenueRPSumByMonths(Date dateFrom, Date dateTo) {
        ArrayList<RevenueReport> list = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "{call revenueReportSumByMonths(?,?)}";
            try (CallableStatement cstmt = connection.prepareCall(sql)) {
                cstmt.setDate(1, dateFrom);
                cstmt.setDate(2, dateTo);
                try (ResultSet rs = cstmt.executeQuery()) {
                    while (rs.next()) {
                        RevenueReport rev = new RevenueReport(rs.getString("monthsOfAYear"), rs.getInt("totalBillPerMonth"), 
                                rs.getInt("totalFundPerMonth"), rs.getInt("totalProfitPerMonth"));
                        list.add(rev);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static Date GetBillOldestDay() {
        Date billOldestDay = null;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "SELECT MIN(Dates) as oldestDate FROM tblBill";
            try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    billOldestDay = rs.getDate("oldestDate");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return billOldestDay;
    }

}
