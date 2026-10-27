package com.latihan.pengajuan_pinjaman_api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PengajuanPinjamanService {

    private void validasi(String nama, double jumlahPengajuan){
        if(nama==null||nama.trim().isEmpty()||jumlahPengajuan<100000){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data Pengajuan Tidak Valid");
        }
    }
    private List<PengajuanPinjaman> daftarPengajuan = new ArrayList<>();

    public PengajuanPinjaman simpanPengajuan(String nama, double jumlahPengajuan) {
        validasi(nama, jumlahPengajuan);
        // 1. Bikin objek baru dari parameter yang diterima
        String id = UUID.randomUUID().toString();
        PengajuanPinjaman pengajuanBaru = new PengajuanPinjaman(id, nama, jumlahPengajuan);

        // 2. Masukin ke List (memory)
        daftarPengajuan.add(pengajuanBaru);

        // 3. Return objek yang baru dibikin
        return pengajuanBaru;
    }

    // Method tambahan, bakal berguna buat Controller nanti
    // kalau mau nampilin SEMUA data yang udah tersimpan (fitur Read)
    public List<PengajuanPinjaman> getSemuaPengajuan() {
        return daftarPengajuan;
    }

    public boolean hapusPengajuan(String id){
        return daftarPengajuan.removeIf(p -> p.getId().equals(id));
    }

    public boolean ubahPengajuan(String id, String nama, double jumlahPengajuan){
        validasi(nama, jumlahPengajuan);
        for(int i=0;i<daftarPengajuan.size();i++){
            if(daftarPengajuan.get(i).getId().equals(id)){
                daftarPengajuan.set(i, new PengajuanPinjaman(id, nama, jumlahPengajuan));
                return true;
            }
        }
        return false;
    }
}