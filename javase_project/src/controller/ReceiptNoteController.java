/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ReceiptNote;

/**
 *
 * @author TechCare
 */
public class ReceiptNoteController {

    public static List<ReceiptNote> GetListReceiptNote() {
        ArrayList<ReceiptNote> list = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblReceiptNote order by Dates desc";
            try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    ReceiptNote receiptNote = new ReceiptNote(rs.getInt("RepID"), rs.getInt("EmpID"), rs.getFloat("RepTotalMoney"),
                            rs.getDate("Dates"), rs.getInt("ProviderID"));
                    list.add(receiptNote);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptNoteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void AddReceiptNote(ReceiptNote newNote) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "insert into tblReceiptNote(EmpID,RepTotalMoney,Dates,ProviderID) values (?,0,getdate(),?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, newNote.getEmpID());
                preparedStatement.setInt(2, newNote.getProviderID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptNoteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void RemoveReceiptNoteByRepID(int RepID) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "delete tblReceiptNote where RepID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, RepID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptNoteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void EditReceiptNote(ReceiptNote receiptNote) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblReceipt set Emp = ? , Dates = ? , ProviderID = ? where RepID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, receiptNote.getEmpID());
                preparedStatement.setDate(2, receiptNote.getDates());
                preparedStatement.setInt(3, receiptNote.getProviderID());
                preparedStatement.setInt(4, receiptNote.getRepID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptNoteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
