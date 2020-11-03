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
import model.Provider;

/**
 *
 * @author stfu
 */
public class ProviderController {

    public static List<Provider> GetListProvider() {
        ArrayList<Provider> list = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblProvider";
            try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Provider provider = new Provider(rs.getInt("ProviderID"), rs.getString("ProviderName"), rs.getString("ProviderAddress"), rs.getString("TellNo"));
                    list.add(provider);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static Provider GetProviderByProviderID(int ProviderID) {
        Provider provider = null;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblProvider where ProviderID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ProviderID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    provider = null;
                    if (rs.next()) {
                        provider = new Provider(rs.getInt("ProviderID"), rs.getString("ProviderName"), rs.getString("ProviderAddress"), rs.getString("TellNo"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provider;
    }

    public static boolean checkExistTellNoProvider(String tellno) {
        boolean check = true;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblProvider where TellNo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tellno);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    check = rs.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public static boolean checkTellNoOnUpdate(int provID, String tellno) {
        boolean check = true;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select ProviderID from tblProvider where TellNo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tellno);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        check = rs.getInt("ProviderID") == provID;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public static void AddProvider(Provider newprovider) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "insert into tblProvider values (?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newprovider.getProviderName());
                preparedStatement.setString(2, newprovider.getProviderAddress());
                preparedStatement.setString(3, newprovider.getTellNo());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void EditProvider(Provider provider) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblProvider set ProviderName = ? , ProviderAddress =? , TellNo = ? where ProviderID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, provider.getProviderName());
                preparedStatement.setString(2, provider.getProviderAddress());
                preparedStatement.setString(3, provider.getTellNo());
                preparedStatement.setInt(4, provider.getProviderID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void RemoveProviderByProviderID(int ProviderID) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "delete tblProvider where ProviderID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ProviderID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProviderController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Xóa dữ liệu thành công", 1, 3).setVisible(true);
    }

}
