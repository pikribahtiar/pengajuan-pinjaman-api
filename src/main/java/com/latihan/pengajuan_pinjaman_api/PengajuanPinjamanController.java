package com.latihan.pengajuan_pinjaman_api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// @RestController: class ini kumpulan endpoint API (sama kayak HelloController)
// @RequestMapping("/pengajuan"): semua endpoint di class ini otomatis diawali
// "/pengajuan" - jadi gak perlu tulis "/pengajuan/..." berulang di tiap method
@RestController
@RequestMapping("/pengajuan")
public class PengajuanPinjamanController {

    // ========================================================
    // INI DIA DEPENDENCY INJECTION yang dibahas kemarin!
    // ========================================================
    // Kita BUTUH PengajuanPinjamanService buat kerja (biar bisa simpen data).
    // Di kode LAMA (cara manual), kita akan nulis:
    //     private PengajuanPinjamanService service = new PengajuanPinjamanService();
    //
    // TAPI, di Spring, kita CUKUP declare field-nya doang,
    // Spring yang OTOMATIS "nyuntikin" object Service yang tepat
    // lewat constructor ini. Makanya constructor ini KOSONG badannya -
    // Spring yang isi parameter "service" otomatis pas aplikasi start.
    private final PengajuanPinjamanService service;

    public PengajuanPinjamanController(PengajuanPinjamanService service) {
        this.service = service;
    }

    // ========================================================
    // ENDPOINT POST - buat nyimpen data baru
    // ========================================================
    // @PostMapping("/simpan") -> alamat lengkapnya jadi: POST /pengajuan/simpan
    // @RequestBody PengajuanPinjaman data -> ini yang BARU:
    //   Spring otomatis baca JSON yang dikirim lewat body request,
    //   terus "ubah" otomatis jadi objek PengajuanPinjaman.
    //   Kita GAK PERLU manual gson.fromJson() lagi kayak kode SP3K dulu!
    @PostMapping("/simpan")
    public ApiResponse<PengajuanPinjaman> simpan(@RequestBody PengajuanPinjaman data) {
//        return service.simpanPengajuan(data.getNama(), data.getJumlahPinjaman());
        PengajuanPinjaman hasil = service.simpanPengajuan(data.getNama(), data.getJumlahPinjaman());

        return ApiResponse.success(hasil);
    }

    // ========================================================
    // ENDPOINT GET - buat nampilin semua data (fitur Read)
    // ========================================================
    // Alamat lengkapnya: GET /pengajuan/semua
    @GetMapping("/semua")
    public ApiResponse<List<PengajuanPinjaman>> semua() {
        return ApiResponse.success(service.getSemuaPengajuan());
    }

    @DeleteMapping("/hapus/{id}")
    public ApiResponse<Void> hapus(@PathVariable String id){
        boolean berhasil = service.hapusPengajuan(id);

        if (!berhasil){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Pengajuan Tidak Ditemukan");
        }
        return ApiResponse.success(null);
    }

    @PutMapping("/ubah/{id}")
    public ApiResponse<Void> ubah(@PathVariable String id, @RequestBody PengajuanPinjaman data){
        boolean berhasil = service.ubahPengajuan(id, data.getNama(), data.getJumlahPinjaman());

        if(!berhasil){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Pengajuan Tidak Ditemukan");
        }
        return ApiResponse.success(null);
    }
}