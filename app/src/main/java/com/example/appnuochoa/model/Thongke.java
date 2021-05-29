package com.example.appnuochoa.model;

import java.io.Serializable;

public class Thongke implements Serializable {
    String thoigian;
    int tongtien;

    public Thongke(String thoigian, int tongtien) {
        this.thoigian = thoigian;
        this.tongtien = tongtien;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
}
