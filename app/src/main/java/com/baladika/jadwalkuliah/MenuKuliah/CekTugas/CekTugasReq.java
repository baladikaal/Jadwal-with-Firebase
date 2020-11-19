package com.baladika.jadwalkuliah.MenuKuliah.CekTugas;

import java.io.Serializable;

public class CekTugasReq implements Serializable {

    private String tugas;
    private String hari;
    private String keterangan;
    private String matkulID;
    private String key;
    private String userId;
    private String SmstrId;

    public String getTugas() {
        return tugas;
    }

    public void setTugas(String tugas) {
        this.tugas = tugas;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
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



    public CekTugasReq(){}

    public CekTugasReq(String tugas, String hari, String keterangan) {
        this.tugas = tugas;
        this.hari = hari;
        this.keterangan = keterangan;
    }
}
