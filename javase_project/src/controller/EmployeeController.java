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
import model.Employee;
import view.Message;

/**
 *
 * @author TechCare
 */
public class EmployeeController {

    public static List<Employee> GetListEmployee() {
        ArrayList<Employee> list = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblEmployee where EmpStatus is null";
            try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Employee e = new Employee(rs.getInt("EmpID"), rs.getString("EmpName"), rs.getDate("DoB"), rs.getString("Gender"),
                            rs.getString("EmpAddress"), rs.getString("TellNo"), rs.getString("Email"), rs.getInt("Salary"));
                    list.add(e);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static boolean checkTellNoEmp(String tellno) {
        boolean check = true;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblEmployee where TellNo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tellno);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    check = rs.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public static boolean checkEmailEmp(String email) {
        boolean check = true;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblEmployee where Email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    check = rs.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public static void AddEmployee(Employee newemp) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "insert into tblEmployee(EmpName,DoB,Gender,EmpAddress,TellNo,Email,Salary) values (?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newemp.getEmpName());
                preparedStatement.setDate(2, newemp.getDoB());
                preparedStatement.setString(3, newemp.getGender());
                preparedStatement.setString(4, newemp.getEmpAdress());
                preparedStatement.setString(5, newemp.getTellNo());
                preparedStatement.setString(6, newemp.getEmail());
                preparedStatement.setDouble(7, newemp.getLuong());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new Message("Thêm dữ liệu thành công", 1, 4).setVisible(true);
    }

    public static void EditEmployee(Employee emp) {
        try {
            try (Connection connection = connectDB.connectSQLServer()) {
                String sql = "update tblEmployee set EmpName = ? , DoB = ? , Gender = ? , EmpAddress =? , TellNo = ? , Email = ? , Salary = ? where EmpId = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, emp.getEmpName());
                    preparedStatement.setDate(2, emp.getDoB());
                    preparedStatement.setString(3, emp.getGender());
                    preparedStatement.setString(4, emp.getEmpAdress());
                    preparedStatement.setString(5, emp.getTellNo());
                    preparedStatement.setString(6, emp.getEmail());
                    preparedStatement.setDouble(7, emp.getLuong());
                    preparedStatement.setInt(8, emp.getEmpID());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new Message("Cập nhật dữ liệu thành công", 1, 4).setVisible(true);
    }

    public static void xoanhanvien(int EmpID) {
        try {
            Connection connection = connectDB.connectSQLServer();
            String sql = "update tblEmployee set EmpStatus = N'Nghỉ' where EmpID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, EmpID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Xóa dữ liệu thành công", 1, 4).setVisible(true);
    }

    public static Employee getEmpByID(int ID) {
        Employee emp = null;
        try {
            Connection connection = connectDB.connectSQLServer();
            String sql = "select * from tblEmployee where EmpId=?";
            try (PreparedStatement ps = connection.prepareCall(sql)) {
                ps.setInt(1, ID);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        emp = new Employee(rs.getInt("EmpID"), rs.getString("EmpName"), rs.getDate("DoB"), rs.getString("Gender"),
                                rs.getString("EmpAddress"), rs.getString("TellNo"), rs.getString("Email"), rs.getInt("Salary"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emp;
    }

    public static String getEmpNamebyEmpID(int empid) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblEmployee where EmpID = ?";
        String empname = null;
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, empid);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        empname = rs.getString("EmpName");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empname;
    }

    /*Hàm kiểm tra số điện thoại cập nhật có trùng với sdt của người khác không?*/
    public static boolean checkTellNoOnUpdate(int empid, String tellno) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select EmpID from tblEmployee where TellNo = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tellno);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (rs.getInt("EmpID") == empid) {
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
    public static boolean checkEmailOnUpdate(int empid, String email) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select EmpID from tblEmployee where Email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (rs.getInt("EmpID") == empid) {
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
