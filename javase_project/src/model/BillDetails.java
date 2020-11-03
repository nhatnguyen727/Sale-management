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
public class BillDetails {
    private int BillID;
    private int ProductID;
    private int QtyonHand;

    public BillDetails() {
    }

    public BillDetails(int BillID, int ProductID, int QtyonHand) {
        this.BillID = BillID;
        this.ProductID = ProductID;
        this.QtyonHand = QtyonHand;
    }

    public int getBillID() {
        return BillID;
    }

    public void setBillID(int BillID) {
        this.BillID = BillID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public int getQtyonHand() {
        return QtyonHand;
    }

    public void setQtyonHand(int QtyonHand) {
        this.QtyonHand = QtyonHand;
    }
    
    
}
