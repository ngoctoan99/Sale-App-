package com.example.demoseminar.Orders;

import java.io.Serializable;

public class DetailOrder implements Serializable {
    private String madon;
    private String tensp;
    private String hinhanh;
    private String trangthai;
    private int giasp;
    private int soluong;
    public DetailOrder(){
    }
    public DetailOrder(String tensp, String hinhanh, String trangthai, int giasp) {
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.trangthai = trangthai;
        this.giasp = giasp;
    }
    public String getMadon() {
        return madon;
    }

    public String getTensp() {
        return tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public int getGiasp() {
        return giasp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setMadon(String madon) {
        this.madon = madon;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
