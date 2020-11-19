package com.baladika.jadwalkuliah.MenuKuliah.Semester;

import java.io.Serializable;

public class SemesterReq implements Serializable {

    private String semester;
    private String tahun;
    private String key;
    private String userId;


    public SemesterReq(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public SemesterReq(String semester, String tahun) {
        this.semester = semester;
        this.tahun = tahun;
    }

    @Override
    public String toString(){
        return ""+semester+"\n"+
                ""+tahun;
    }
}
