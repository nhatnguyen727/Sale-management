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
import model.BillDetails;

/**
 *
 * @author TechCare
 */
public class BillDetailsController {

    public static List<BillDetails> GetListBillDetails() throws SQLException {
        ArrayList<BillDetails> list = new ArrayList<>();
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblBillDetails";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            BillDetails billDetails = new BillDetails(rs.getInt("BillID"), rs.getInt("ProductID"), rs.getInt("QtyonHand"));
            list.add(billDetails);
        }
        rs.close();
        statement.close();
        connection.close();
        return list;
    }

    public static List<BillDetails> GetListBillDetailsByBillID(int BillID) throws SQLException {
        ArrayList<BillDetails> list = new ArrayList<>();
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblBillDetails where BillID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, BillID);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            BillDetails billDetails = new BillDetails(rs.getInt("BillID"), rs.getInt("ProductID"), rs.getInt("QtyonHand"));
            list.add(billDetails);
        }
        rs.close();
        preparedStatement.close();
        connection.close();
        return list;
    }

    public static void AddBillDetails(BillDetails newline) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "insert into tblBillDetails values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, newline.getBillID());
        preparedStatement.setInt(2, newline.getProductID());
        preparedStatement.setInt(3, newline.getQtyonHand());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void EditBillDetails_ChangeQtyonHand(BillDetails editline) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblBillDetails set QtyonHand = ? where ProductID =? and BillID =?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, editline.getQtyonHand());
                preparedStatement.setInt(2, editline.getProductID());
                preparedStatement.setInt(3, editline.getBillID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void RemoveBillDetails(BillDetails deleteline) throws SQLException {
        Connection connection = connectDB.connectSQLServer();
        String sql = "delete tblBillDetails where ProductID =? and BillID =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, deleteline.getProductID());
        preparedStatement.setInt(2, deleteline.getBillID());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    /*Kiểm tra xem mặt hàng đã được thêm vào hóa đơn chưa*/
    public static boolean checkExistBillDetails(int billid, int productid) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblBillDetails where ProductID =? and BillID =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(2, billid);
            preparedStatement.setInt(1, productid);
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
        } catch (SQLException ex) {
            Logger.getLogger(BillDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
