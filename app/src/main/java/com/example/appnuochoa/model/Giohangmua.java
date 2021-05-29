package com.example.appnuochoa.model;

public class Giohangmua {
    int idgh;
    String tensp;
    long giasp;
    String hinhsp;
    int soluongsp;

    public Giohangmua(int idgh, String tensp, long giasp, String hinhsp, int soluongsp) {
        this.idgh = idgh;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.soluongsp = soluongsp;
    }


    public int getIdgh() {
        return idgh;
    }

    public void setIdgh(int idgh) {
        this.idgh = idgh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }
}