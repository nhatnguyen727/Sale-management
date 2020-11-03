/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author TechCare
 */
public class Product {
    private int ProductID;
    private String ProductName;
    private float UnitPrice;
    private int QtyonHand;

    public Product() {
    }

    public Product(int ProductID, String ProductName, float UnitPrice, int QtyonHand) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.UnitPrice = UnitPrice;
        this.QtyonHand = QtyonHand;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public float getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(float UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public int getQtyonHand() {
        return QtyonHand;
    }

    public void setQtyonHand(int QtyonHand) {
        this.QtyonHand = QtyonHand;
    }
   

    
}
    
    
    