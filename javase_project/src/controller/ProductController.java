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
import model.Product;

/**
 *
 * @author TechCare
 */
public class ProductController {

    public static List<Product> GetListProduct() {
        ArrayList<Product> list = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblProduct where ProductStatus is null";
            try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Product product = new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getInt("UnitPrice"), rs.getInt("QtyonHand"));
                    list.add(product);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static Product GetProductByProductID(int id) {
        Product p = null;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblProduct where ProductID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        p = new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getInt("UnitPrice"), rs.getInt("QtyonHand"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public static void AddProduct(Product newproduct) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "insert into tblProduct(ProductName,UnitPrice,QtyOnHand) values (?,?,0)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newproduct.getProductName());
                preparedStatement.setFloat(2, newproduct.getUnitPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    public static void RemoveProductByProductID(int ProductID) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblProduct set ProductStatus = N'Nghỉ' where ProductID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ProductID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    public static void EditProduct(Product p) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblProduct set ProductName = ? , UnitPrice =? where ProductID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, p.getProductName());
                preparedStatement.setDouble(2, p.getUnitPrice());
                preparedStatement.setInt(3, p.getProductID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    public static Product isProductExists(String productName) {
        Product p = null;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "SELECT * FROM tblProduct WHERE ProductName='" + productName + "'";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    if (rs.getString("ProductName").equals(productName)) {
                        p = new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getInt("UnitPrice"), rs.getInt("QtyonHand"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public static int getQtyOnHandByProductID(int productid) {
        int quantity = 0;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select QtyOnHand from tblProduct where ProductID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, productid);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        quantity = rs.getInt("QtyOnHand");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantity;
    }
}
