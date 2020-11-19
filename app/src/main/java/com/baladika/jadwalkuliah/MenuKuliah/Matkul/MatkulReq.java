package com.baladika.jadwalkuliah.MenuKuliah.Matkul;

import java.io.Serializable;

public class MatkulReq implements Serializable {

    private String matkul;
    private String ruangan;
    private String jam;
    private String hari;
    private String key;
    private String userId;
    private String SmstrId;

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }




    public String getSmstrId() {
        return SmstrId;
    }

    public void setSmstrId(String smstrId) {
        SmstrId = smstrId;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public MatkulReq(){

    }

    public String getMatkul() {
        return matkul;
    }

    public void setMatkul(String matkul) {
        this.matkul = matkul;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MatkulReq(String matkul, String ruangan, String jam,String hari) {
        this.matkul = matkul;
        this.ruangan = ruangan;
        this.jam = jam;
        this.hari = hari;
    }
}
