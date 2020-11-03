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
import model.Position;

/**
 *
 * @author TechCare
 */
public class PositionController {

    public static List<Position> GetListPosition() {
        ArrayList<Position> list = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblPosition";
            try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Position p = new Position(rs.getInt("PosID"), rs.getString("PosName"));
                    list.add(p);
                }
            }
        } catch (SQLException ex) {
            new view.Message(ex.getMessage(), 2, 1).setVisible(true);
            Logger.getLogger(PositionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void AddPosition(Position newposition) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "insert into tblPosition values (?)";
            try (PreparedStatement preparedStatemente = connection.prepareStatement(sql)) {
                preparedStatemente.setString(1, newposition.getPosName());
                preparedStatemente.executeUpdate();
                new view.Message("Thêm dữ liệu thành công", 1, 1).setVisible(true);
            }
        } catch (SQLException ex) {
            new view.Message(ex.getMessage(), 2, 1).setVisible(true);
            Logger.getLogger(PositionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void RemovePositionByPosID(int posid) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "delete tblPosition where PosID =?";
            try (PreparedStatement preparedStatemente = connection.prepareStatement(sql)) {
                preparedStatemente.setInt(1, posid);
                preparedStatemente.executeUpdate();
                new view.Message("Xóa dữ liệu thành công", 1, 1).setVisible(true);
            }
        } catch (SQLException ex) {
            new view.Message(ex.getMessage(), 2, 1).setVisible(true);
            Logger.getLogger(PositionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void EditPosition(Position pos) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblPosition set PosName =? where PosID =?";
            try (PreparedStatement preparedStatemente = connection.prepareStatement(sql)) {
                preparedStatemente.setString(1, pos.getPosName());
                preparedStatemente.setInt(2, pos.getPosId());
                preparedStatemente.executeUpdate();
                new view.Message("Cập nhật dữ liệu thành công", 1, 1).setVisible(true);
            }
        } catch (SQLException ex) {
            new view.Message(ex.getMessage(), 2, 1).setVisible(true);
            Logger.getLogger(PositionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int GetPosidbyPosname(String Posname) {
        int posid = 0;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select PosID from tblPosition where Posname = ?";
            try (PreparedStatement preparedStatemente = connection.prepareStatement(sql)) {
                preparedStatemente.setString(1, Posname);
                try (ResultSet rs = preparedStatemente.executeQuery()) {
                    while (rs.next()) {
                        posid = rs.getInt("PosID");
                    }
                }
            }
        } catch (SQLException ex) {
            new view.Message(ex.getMessage(), 2, 1).setVisible(true);
            Logger.getLogger(PositionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posid;
    }

    public static boolean checkExistPosition(String PosName) {
        boolean check = true;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblPosition where PosName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, PosName);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    check = rs.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Accountcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public static String getPosnamebyPosID(int posid) {
        Connection connection = connectDB.connectSQLServer();
        String sql = "select * from tblPosition where PosID = ?";
        String posname = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posid);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                posname = rs.getString("PosName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PositionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posname;
    }
}
