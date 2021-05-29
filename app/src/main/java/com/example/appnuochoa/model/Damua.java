package com.example.appnuochoa.model;

import java.io.Serializable;
import java.util.Date;

public class Damua implements Serializable {
    int id;
    int makh;
    String ngaymua;
    String trangthai;
    int masp;
    String tensp;
    int soluong;
    int giaban;
    String hinhanh;

    public Damua(int id, int makh, String ngaymua, String trangthai, int masp, String tensp, int soluong, int giaban, String hinhanh) {
        this.id = id;
        this.makh = makh;
        this.ngaymua = ngaymua;
        this.trangthai = trangthai;
        this.masp = masp;
        this.tensp = tensp;
        this.soluong = soluong;
        this.giaban = giaban;
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
