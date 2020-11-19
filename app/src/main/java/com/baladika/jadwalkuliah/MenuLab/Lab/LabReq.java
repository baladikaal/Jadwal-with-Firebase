package com.baladika.jadwalkuliah.MenuLab.Lab;

import java.io.Serializable;

public class LabReq implements Serializable {

    private String lab;
    private String ruangan;
    private String jam;
    private String hari;
    private String note;
    private String key;
    private String userId;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
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

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public LabReq(){}

    public LabReq(String lab, String ruangan, String jam, String hari, String note) {
        this.lab = lab;
        this.ruangan = ruangan;
        this.jam = jam;
        this.hari = hari;
        this.note = note;
    }
}
