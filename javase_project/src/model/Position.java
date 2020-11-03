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
public class Position {
    private int PosId;
    private String PosName;

    public Position() {
    }

    public Position(int PosId, String PosName) {
        this.PosId = PosId;
        this.PosName = PosName;
    }

    public int getPosId() {
        return PosId;
    }

    public void setPosId(int PosId) {
        this.PosId = PosId;
    }

    public String getPosName() {
        return PosName;
    }

    public void setPosName(String PosName) {
        this.PosName = PosName;
    }

   
    
}
