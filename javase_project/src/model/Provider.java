/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author stfu
 */
public class Provider {

    private int ProviderID;
    private String ProviderName;
    private String ProviderAddress;
    private String TellNo;

    public Provider() {
    }

    public Provider(int ProviderID, String ProviderName, String ProviderAddress, String ProviderPhone) {
        this.ProviderID = ProviderID;
        this.ProviderName = ProviderName;
        this.ProviderAddress = ProviderAddress;
        this.TellNo = ProviderPhone;
    }

    public int getProviderID() {
        return ProviderID;
    }

    public void setProviderID(int ProviderID) {
        this.ProviderID = ProviderID;
    }

    public String getProviderName() {
        return ProviderName;
    }

    public void setProviderName(String ProviderName) {
        this.ProviderName = ProviderName;
    }

    public String getProviderAddress() {
        return ProviderAddress;
    }

    public void setProviderAddress(String ProviderAddress) {
        this.ProviderAddress = ProviderAddress;
    }

    public String getTellNo() {
        return TellNo;
    }

    public void setTellNo(String TellNo) {
        this.TellNo = TellNo;
    }

}
