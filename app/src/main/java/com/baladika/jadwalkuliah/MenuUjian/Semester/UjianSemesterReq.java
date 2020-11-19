package com.baladika.jadwalkuliah.MenuUjian.Semester;

import java.io.Serializable;

public class UjianSemesterReq implements Serializable {

    private String Usemester;
    private String Utahun;
    private String key;
    private String userId;

    public String getUsemester() {
        return Usemester;
    }

    public void setUsemester(String usemester) {
        Usemester = usemester;
    }

    public String getUtahun() {
        return Utahun;
    }

    public void setUtahun(String utahun) {
        Utahun = utahun;
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

    public UjianSemesterReq(){

    }

    public UjianSemesterReq(String usemester, String utahun) {
        Usemester = usemester;
        Utahun = utahun;
    }


}