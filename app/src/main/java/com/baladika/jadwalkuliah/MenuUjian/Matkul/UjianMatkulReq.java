package com.baladika.jadwalkuliah.MenuUjian.Matkul;

import java.io.Serializable;

public class UjianMatkulReq implements Serializable {

    private String Umatkul;
    private String Uruangan;
    private String Ujam;
    private String Uhari;
    private String key;

    public UjianMatkulReq(){}

    private String userId;
    private String SmstrId;

    private String isi;

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getUmatkul() {
        return Umatkul;
    }

    public void setUmatkul(String umatkul) {
        Umatkul = umatkul;
    }

    public String getUruangan() {
        return Uruangan;
    }

    public void setUruangan(String uruangan) {
        Uruangan = uruangan;
    }

    public String getUjam() {
        return Ujam;
    }

    public void setUjam(String ujam) {
        Ujam = ujam;
    }

    public String getUhari() {
        return Uhari;
    }

    public void setUhari(String uhari) {
        Uhari = uhari;
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



    public UjianMatkulReq(String umatkul, String uruangan, String ujam, String uhari) {
        Umatkul = umatkul;
        Uruangan = uruangan;
        Ujam = ujam;
        Uhari = uhari;
    }


}
