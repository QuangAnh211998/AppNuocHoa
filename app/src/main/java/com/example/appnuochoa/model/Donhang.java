package com.example.appnuochoa.model;

import java.io.Serializable;

public class Donhang implements Serializable {
    int iddh;
    String hoten;
    String sdt;
    String diachi;
    String ngaydat;
    int tongtien;
    String trangthai;

    public Donhang(int iddh, String hoten, String sdt, String diachi, String ngaydat, int tongtien, String trangthai) {
        this.iddh = iddh;
        this.hoten = hoten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.ngaydat = ngaydat;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public int getIddh() {
        return iddh;
    }

    public void setIddh(int iddh) {
        this.iddh = iddh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
