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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;

/**
 *
 * @author TechCare
 */
public class CustomerController {

    public static List<Customer> GetListCustomer() throws SQLException {
        ArrayList<Customer> list = new ArrayList<>();
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblCustomer";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Customer customer = new Customer(rs.getInt("CustID"), rs.getString("CustName"), rs.getString("Gender"), rs.getString("CustAddress"), rs.getString("TellNo"), rs.getString("Email"));
            list.add(customer);
        }
        rs.close();
        statement.close();
        connection.close();
        return list;
    }

    public static Customer GetCustomerByCustID(int CustID) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblCustomer where CustID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, CustID);
        ResultSet rs = preparedStatement.executeQuery();
        Customer customer = null;
        if (rs.next()) {
            customer = new Customer(rs.getInt("CustID"), rs.getString("CustName"), rs.getString("Gender"), rs.getString("CustAddress"), rs.getString("TellNo"), rs.getString("Email"));
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return customer;
    }

    public static String getCusNamebyCusid(int custid) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblCustomer where CustID = ?";
        PreparedStatement preparedStatement;
        String custname = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, custid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                custname = rs.getString("CustName");
            }
            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return custname;
    }

    public static boolean checkExistTellNoCust(String tellno) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblCustomer where TellNo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, tellno);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            rs.close();
            preparedStatement.close();
            connection.close();
            return true;
        } else {
            rs.close();
            preparedStatement.close();
            connection.close();
            return false;
        }
    }

    public static boolean checkExistEmailCust(String email) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblCustomer where Email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            rs.close();
            preparedStatement.close();
            connection.close();
            return true;
        } else {
            rs.close();
            preparedStatement.close();
            connection.close();
            return false;
        }
    }

    public static void AddCustomer(Customer newcustomer) throws SQLException {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "insert into tblCustomer values (?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newcustomer.getCustName());
                preparedStatement.setString(2, newcustomer.getGender());
                preparedStatement.setString(3, newcustomer.getCustAddress());
                preparedStatement.setString(4, newcustomer.getTellNo());
                preparedStatement.setString(5, newcustomer.getEmail());
                preparedStatement.executeUpdate();
            }
        }
    }

    public static void EditCustomer(Customer customer) throws SQLException {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblCustomer set CustName = ? , Gender = ? , CustAddress =? , TellNo = ? , Email = ? where CustID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, customer.getCustName());
                preparedStatement.setString(2, customer.getGender());
                preparedStatement.setString(3, customer.getCustAddress());
                preparedStatement.setString(4, customer.getTellNo());
                preparedStatement.setString(5, customer.getEmail());
                preparedStatement.setInt(6, customer.getCustID());
                preparedStatement.executeUpdate();
            }
        }
    }

    public static void RemoveCustomerByCustID(int CustID) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "delete tblCustomer where CustID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, CustID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Xóa dữ liệu thành công", 1, 3).setVisible(true);
    }

    /*Hàm kiểm tra số điện thoại cập nhật có trùng với sdt của người khác không?*/
    public static boolean checkTellNoOnUpdate(int custid, String tellno) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select CustID from tblCustomer where TellNo = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tellno);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (rs.getInt("CustID") == custid) {
                    rs.close();
                    preparedStatement.close();
                    connection.close();
                    return true;
                } else {
                    rs.close();
                    preparedStatement.close();
                    connection.close();
                    return false;
                }
            } else {
                rs.close();
                preparedStatement.close();
                connection.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /*Hàm kiểm tra email cập nhật có trùng với sdt của người khác không?*/
    public static boolean checkEmailOnUpdate(int custid, String email) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select CustID from tblCustomer where Email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (rs.getInt("CustID") == custid) {
                    rs.close();
                    preparedStatement.close();
                    connection.close();
                    return true;
                } else {
                    rs.close();
                    preparedStatement.close();
                    connection.close();
                    return false;
                }
            } else {
                rs.close();
                preparedStatement.close();
                connection.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
