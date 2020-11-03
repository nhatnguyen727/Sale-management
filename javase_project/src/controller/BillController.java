/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Bill;

/**
 *
 * @author TechCare
 */
public class BillController {

    static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static List<Bill> GetListBill() throws SQLException {
        ArrayList<Bill> list = new ArrayList<>();
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblBill order by Dates desc";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Bill bill = new Bill(rs.getInt("BillID"), rs.getInt("EmpID"), rs.getInt("TotalMoney"), rs.getDate("Dates"), rs.getInt("CustID"));
            list.add(bill);
        }
        rs.close();
        statement.close();
        connection.close();
        return list;
    }

    public static void AddBill(Bill newbill) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "insert into tblBill(EmpID,TotalMoney,Dates,CustID) values (?,0,getdate(),?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, newbill.getEmpID());
        preparedStatement.setInt(2, newbill.getCustID());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void RemoveBillByBillID(int BillID) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "delete tblBill where BillID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, BillID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int EditBill(Bill newbill) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "update Bill set EmpId = ? ,TotalMoney = ? , Dates = ? , CustID = ? where BillID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, newbill.getBillID());
        preparedStatement.setDouble(2, newbill.getTotalMoney());
        preparedStatement.setDate(3, newbill.getDates());
        preparedStatement.setInt(4, newbill.getCustID());
        preparedStatement.setInt(5, newbill.getBillID());
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result;
    }
    
}
