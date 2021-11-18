package com.example.demoseminar.Orders;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String madon;
    private String diachikh;
    private String tenkh;
    private String sdtkh;
    private String ngaydat;
    private String trangthai;
    private  int soluong;
    private int tongtien;
    private List<DetailOrder> detailOrderList;
    public Order(){}
    public Order(String diachikh, String tenkh, String sdtkh, String ngaydat, String trangthai, int soluong, int tongtien, List<DetailOrder> detailOrderList) {
        this.diachikh = diachikh;
        this.tenkh = tenkh;
        this.sdtkh = sdtkh;
        this.ngaydat = ngaydat;
        this.trangthai = trangthai;
        this.soluong = soluong;
        this.tongtien = tongtien;
        this.detailOrderList = detailOrderList;
    }

    public String getMadon() {
        return madon;
    }

    public String getDiachikh() {
        return diachikh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public String getSdtkh() {
        return sdtkh;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public int getSoluong() {
        return soluong;
    }

    public int getTongtien() {
        return tongtien;
    }

    public List<DetailOrder> getDetailOrderList() {
        return detailOrderList;
    }

    public void setMadon(String madon) {
        this.madon = madon;
    }

    public void setDiachikh(String diachikh) {
        this.diachikh = diachikh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public void setSdtkh(String sdtkh) {
        this.sdtkh = sdtkh;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public void setDetailOrderList(List<DetailOrder> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }
    public  void addListDetailOrder(DetailOrder detailOrder){
        if(this.detailOrderList == null){
            this.detailOrderList = new ArrayList<>();
        }
        this.detailOrderList.add(detailOrder);
    }
}
