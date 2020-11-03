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
import model.ReceiptNoteDetails;

/**
 *
 * @author TechCare
 */
public class ReceiptNoteDetailsController {

    public static List<ReceiptNoteDetails> GetListReceiptNoteDetails() throws SQLException {
        ArrayList<ReceiptNoteDetails> list = new ArrayList<>();
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblReceiptNoteDetail join tblReceiptNote on tblReceiptNoteDetail.RepID = tblReceiptNote.RepID order by Dates desc";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            ReceiptNoteDetails noteDetails = new ReceiptNoteDetails(rs.getInt("RepID"), rs.getInt("ProductID"), rs.getInt("QtyReceipt"), rs.getFloat("RepPrice"));
            list.add(noteDetails);
        }
        rs.close();
        statement.close();
        connection.close();
        return list;
    }

    public static List<ReceiptNoteDetails> GetListReceiptNoteDetailsByRepID(int repid) throws SQLException {
        ArrayList<ReceiptNoteDetails> list = new ArrayList<>();
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblReceiptNoteDetails where RepID =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, repid);
        ResultSet rs = preparedStatement.executeQuery(sql);
        while (rs.next()) {
            ReceiptNoteDetails noteDetails = new ReceiptNoteDetails(rs.getInt("RepID"), rs.getInt("ProductID"), rs.getInt("QtyReceipt"), rs.getFloat("RepPrice"));
            list.add(noteDetails);
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return list;
    }

    public static int AddReceiptNoteDetail(ReceiptNoteDetails details) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "insert into tblReceiptNoteDetail values (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, details.getRepID());
        preparedStatement.setInt(2, details.getProductID());
        preparedStatement.setInt(3, details.getQtyReceipt());
        preparedStatement.setFloat(4, details.getRepPrice());
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result;
    }

    public static int RemoveReceiptNoteDetail(ReceiptNoteDetails details) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "delete tblReceiptNoteDetail where RepID = ? and ProductID =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, details.getRepID());
        preparedStatement.setInt(2, details.getProductID());
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result;
    }

    public static void EditReceiptNoteDetail(ReceiptNoteDetails details) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "update tblReceiptNoteDetail set QtyReceipt = ?, RepPrice = ? where RepID =? and ProductID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, details.getQtyReceipt());
        preparedStatement.setFloat(2, details.getRepPrice());
        preparedStatement.setInt(3, details.getRepID());
        preparedStatement.setInt(4, details.getProductID());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static boolean checkexistReceiptNoteDetals(int repid, int productid) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblReceiptNoteDetail where RepID =? and ProductID =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, repid);
            preparedStatement.setInt(2, productid);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceiptNoteDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
