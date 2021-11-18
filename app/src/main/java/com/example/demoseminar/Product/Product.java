package com.example.demoseminar.Product;

public class Product {
    private String id ;
    private String hinhanh;
    private String tensp;
    private String mota;
    private String loai;
    private int giasp;
    private int slsp = 1;

    public Product(){}
    public Product(String id, String hinhanh, String tensp, String mota, String loai, int giasp) {
        this.id = id;
        this.hinhanh = hinhanh;
        this.tensp = tensp;
        this.mota = mota;
        this.loai = loai;
        this.giasp = giasp;
    }

    public int getSlsp() {
        return slsp;
    }

    public void setSlsp(int slsp) {
        this.slsp = slsp;
    }

    public String getId() {
        return id;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public String getTensp() {
        return tensp;
    }

    public String getMota() {
        return mota;
    }

    public String getLoai() {
        return loai;
    }

    public int getGiasp() {
        return giasp;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

}
