package com.baladika.jadwalkuliah.MenuKuliah.Catatan;

import java.io.Serializable;

public class CatatanReq implements Serializable {

    private String catatan;
    private String keterangan;
    private String tgl;
    private String matkulID;
    private String key;
    private String userId;
    private String SmstrId;


    public CatatanReq(String catatan, String keterangan, String tgl) {
        this.catatan = catatan;
        this.keterangan = keterangan;
        this.tgl = tgl;
    }


    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }



    public CatatanReq(){}




    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getMatkulID() {
        return matkulID;
    }

    public void setMatkulID(String matkulID) {
        this.matkulID = matkulID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSmstrId() {
        return SmstrId;
    }

    public void setSmstrId(String smstrId) {
        SmstrId = smstrId;
    }
}
