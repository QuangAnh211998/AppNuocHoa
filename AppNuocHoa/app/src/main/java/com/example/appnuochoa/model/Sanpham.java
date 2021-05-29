package com.example.appnuochoa.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int Id;
    public String Tensp;
    public String Loaisp;
    public String Xuatxu;
    public String Hinhanh;
    public String Huongsp;
    public Integer Soluong;
    public Integer Giaban;
    public String Mota;


    public Sanpham(int id, String tensp, String loaisp, String xuatxu, String hinhanh, String huongsp, Integer soluong,
                   Integer giaban, String mota) {
        Id = id;
        Tensp = tensp;
        Loaisp = loaisp;
        Xuatxu = xuatxu;
        Hinhanh = hinhanh;
        Huongsp = huongsp;
        Soluong = soluong;
        Giaban = giaban;
        Mota = mota;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public String getLoaisp() {
        return Loaisp;
    }

    public void setLoaisp(String loaisp) {
        Loaisp = loaisp;
    }

    public String getXuatxu() {
        return Xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        Xuatxu = xuatxu;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }

    public String getHuongsp() {
        return Huongsp;
    }

    public void setHuongsp(String huongsp) {
        Huongsp = huongsp;
    }

    public Integer getSoluong() {
        return Soluong;
    }

    public void setSoluong(Integer soluong) {
        Soluong = soluong;
    }

    public Integer getGiaban() {
        return Giaban;
    }

    public void setGiaban(Integer giaban) {
        Giaban = giaban;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }
}
