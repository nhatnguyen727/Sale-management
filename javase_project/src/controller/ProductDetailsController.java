/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.ProductDetails;

/**
 *
 * @author TechCare
 */
public class ProductDetailsController {

    public static List<ProductDetails> GetListProductDetails() {
        ArrayList<ProductDetails> list = new ArrayList<>();
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblProductDetail";
            try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    ProductDetails productDetails = new ProductDetails(rs.getInt("ProductID"), null, rs.getString("ProcessorCPU"), rs.getString("Ram"),
                            rs.getString("Sizes"), rs.getString("Weights"), rs.getString("HDD_SSD"), rs.getString("GPU"), rs.getString("Battery"));
                    list.add(productDetails);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static ProductDetails GetProductDetailsByProductID(int ProductID) {
        ProductDetails productDetails = null;
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "select * from tblProductDetail where ProductID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, ProductID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        productDetails = new ProductDetails(rs.getInt("ProductID"), null, rs.getString("ProcessorCPU"), rs.getString("Ram"),
                                rs.getString("Sizes"), rs.getString("Weights"), rs.getString("HDD_SSD"), rs.getString("GPU"), rs.getString("Battery"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productDetails;
    }

    public static void AddProductDetails(ProductDetails details) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "insert into tblProductDetail values (?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, details.getProductId());
                preparedStatement.setBlob(2, details.getPicture());
                preparedStatement.setString(3, details.getProcessorCPU());
                preparedStatement.setString(4, details.getRam());
                preparedStatement.setString(5, details.getSizes());
                preparedStatement.setString(6, details.getWeights());
                preparedStatement.setString(7, details.getHDD_SSD());
                preparedStatement.setString(8, details.getGPU());
                preparedStatement.setString(9, details.getBattery());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Thêm dữ liệu thành công", 1, 5).setVisible(true);
    }

    public static void RemoveProductDetailsByProductID(int productid) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "delete tblProductDetail where ProductID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, productid);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Xóa dữ liệu thành công", 1, 5).setVisible(true);
    }

    public static void EditProductDetails(ProductDetails details) {
        try (Connection connection = connectDB.connectSQLServer()) {
            String sql = "update tblProductDetail set Picture=?, ProcessorCPU=?, Ram=?, Sizes=?, Weights=?, HDD_SSD=?, GPU=?, Battery=? where ProductID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBlob(1, details.getPicture());
                preparedStatement.setString(2, details.getProcessorCPU());
                preparedStatement.setString(3, details.getRam());
                preparedStatement.setString(4, details.getSizes());
                preparedStatement.setString(5, details.getWeights());
                preparedStatement.setString(6, details.getHDD_SSD());
                preparedStatement.setString(7, details.getGPU());
                preparedStatement.setString(8, details.getBattery());
                preparedStatement.setInt(9, details.getProductId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        new view.Message("Cập nhật dữ liệu thành công", 1, 5).setVisible(true);
    }

    public static ImageIcon GetBlob(int productID, int dim) {
        ImageIcon img = null;
        InputStream imgStream;
        try (Connection conn = connectDB.connectSQLServer()) {
            String sql = "select * from tblProductDetail where ProductID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, productID);
                try (ResultSet res = preparedStatement.executeQuery()) {
                    if (res.next()) {
                        if ((imgStream = res.getBinaryStream("Picture")) != null) {// res.getBinaryStream("Picture") can't do twice ('data has been accessed' exception)
                            BufferedImage buffImg = ImageIO.read(imgStream);
                            img = new ImageIcon(buffImg);
                            Image resizedImage = img.getImage().getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
                            img = new ImageIcon(resizedImage);
                        }
                    }
                }
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ProductDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
}
