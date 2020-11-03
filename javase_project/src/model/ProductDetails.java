/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.InputStream;

/**
 *
 * @author TechCare
 */
public class ProductDetails {
    private int ProductId;
    private InputStream Picture;
    private String ProcessorCPU;
    private String Ram;
    private String Sizes;
    private String Weights;
    private String HDD_SSD;
    private String GPU;
    private String Battery;

    public ProductDetails() {
    }

    public ProductDetails(int ProductId, InputStream Picture, String ProcessorCPU, String Ram, String Sizes, String Weights, String HDD_SSD, String GPU, String Battery) {
        this.ProductId = ProductId;
        this.Picture = Picture;
        this.ProcessorCPU = ProcessorCPU;
        this.Ram = Ram;
        this.Sizes = Sizes;
        this.Weights = Weights;
        this.HDD_SSD = HDD_SSD;
        this.GPU = GPU;
        this.Battery = Battery;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    public InputStream getPicture() {
        return Picture;
    }

    public void setPicture(InputStream Picture) {
        this.Picture = Picture;
    }

    public String getProcessorCPU() {
        return ProcessorCPU;
    }

    public void setProcessorCPU(String ProcessorCPU) {
        this.ProcessorCPU = ProcessorCPU;
    }

    public String getRam() {
        return Ram;
    }

    public void setRam(String Ram) {
        this.Ram = Ram;
    }

    public String getSizes() {
        return Sizes;
    }

    public void setSizes(String Sizes) {
        this.Sizes = Sizes;
    }

    public String getWeights() {
        return Weights;
    }

    public void setWeights(String Weights) {
        this.Weights = Weights;
    }

    public String getHDD_SSD() {
        return HDD_SSD;
    }

    public void setHDD_SSD(String HDD_SSD) {
        this.HDD_SSD = HDD_SSD;
    }

    public String getGPU() {
        return GPU;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String Battery) {
        this.Battery = Battery;
    }

}
