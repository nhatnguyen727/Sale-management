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
public class ReceiptNoteDetails {
    private int RepID;
    private int ProductID;
    private int QtyReceipt;
    private float RepPrice;

    public ReceiptNoteDetails(int RepID, int ProductID, int QtyReceipt, float RepPrice) {
        this.RepID = RepID;
        this.ProductID = ProductID;
        this.QtyReceipt = QtyReceipt;
        this.RepPrice = RepPrice;
    }

    public ReceiptNoteDetails() {
    }

    public float getRepPrice() {
        return RepPrice;
    }

    public void setRepPrice(float RepPrice) {
        this.RepPrice = RepPrice;
    }

   

    public int getRepID() {
        return RepID;
    }

    public void setRepID(int RepID) {
        this.RepID = RepID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public int getQtyReceipt() {
        return QtyReceipt;
    }

    public void setQtyReceipt(int QtyReceipt) {
        this.QtyReceipt = QtyReceipt;
    }
    

    
}
