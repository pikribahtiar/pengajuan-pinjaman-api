package com.latihan.pengajuan_pinjaman_api;

public class PengajuanPinjaman {
    private String id;
    private String nama;
    private double jumlahPinjaman;

    public PengajuanPinjaman(String id, String nama, double jumlahPinjaman){
        this.id = id;
        this.nama = nama;
        this.jumlahPinjaman = jumlahPinjaman;
    }

    public String getId(){
        return id;
    }

    public String getNama(){
        return nama;
    }

    public double getJumlahPinjaman(){
        return jumlahPinjaman;
    }
}
