# Pengajuan Pinjaman API

REST API sederhana untuk mengelola data pengajuan pinjaman, dibuat dengan Spring Boot. Proyek latihan untuk mendalami pembuatan REST API: operasi CRUD, validasi input, dan format response yang konsisten.

## Teknologi

- Java 17
- Spring Boot 4.1.1 (Spring Web MVC)
- Maven (termasuk Maven Wrapper, tidak perlu install Maven terpisah)

## Fitur

- CRUD data pengajuan pinjaman (data disimpan di memori)
- Setiap pengajuan memiliki `id` unik (UUID) yang dibuat oleh server
- Validasi input: nama wajib diisi dan jumlah pinjaman minimal Rp100.000
- Format response seragam untuk sukses maupun gagal
- Status HTTP sesuai hasil proses (`400` untuk input tidak valid, `404` untuk data tidak ditemukan)

## Endpoint

| Method | URL | Fungsi |
|---|---|---|
| POST | `/pengajuan/simpan` | Menyimpan pengajuan baru |
| GET | `/pengajuan/semua` | Menampilkan semua pengajuan |
| PUT | `/pengajuan/ubah/{id}` | Mengubah nama dan jumlah pinjaman |
| DELETE | `/pengajuan/hapus/{id}` | Menghapus pengajuan |

## Format Response

Semua endpoint mengembalikan struktur yang sama:

| Field | Keterangan |
|---|---|
| `errorCode` | `"00"` jika sukses, `"01"` jika gagal |
| `errorMessage` | Pesan hasil proses |
| `object` | Data hasil proses, `null` jika tidak ada data |

### Menyimpan pengajuan

```
POST /pengajuan/simpan
Content-Type: application/json
```

```json
{
  "nama": "Andi",
  "jumlahPinjaman": 500000
}
```

Response sukses (HTTP 200):

```json
{
  "errorCode": "00",
  "errorMessage": "Success",
  "object": {
    "id": "6ccc3aa9-bf69-4cdd-b1fa-a66de443e219",
    "nama": "Andi",
    "jumlahPinjaman": 500000.0
  }
}
```

Response gagal, misalnya jumlah pinjaman di bawah Rp100.000 (HTTP 400):

```json
{
  "errorCode": "01",
  "errorMessage": "Data Pengajuan Tidak Valid",
  "object": null
}
```

### Menampilkan semua pengajuan

```
GET /pengajuan/semua
```

Response (HTTP 200):

```json
{
  "errorCode": "00",
  "errorMessage": "Success",
  "object": [
    {
      "id": "ecceb910-2b3c-4087-a32d-4c05ff15f358",
      "nama": "Joni",
      "jumlahPinjaman": 700000.0
    },
    {
      "id": "6ccc3aa9-bf69-4cdd-b1fa-a66de443e219",
      "nama": "Andi",
      "jumlahPinjaman": 500000.0
    }
  ]
}
```

### Mengubah pengajuan

```
PUT /pengajuan/ubah/{id}
Content-Type: application/json
```

```json
{
  "nama": "Joni",
  "jumlahPinjaman": 3000000
}
```

Response sukses (HTTP 200):

```json
{
  "errorCode": "00",
  "errorMessage": "Success",
  "object": null
}
```

### Menghapus pengajuan

```
DELETE /pengajuan/hapus/{id}
```

Response sukses (HTTP 200) sama seperti perubahan di atas.

Jika `id` tidak ditemukan, endpoint PUT dan DELETE mengembalikan HTTP 404:

```json
{
  "errorCode": "01",
  "errorMessage": "Data Pengajuan Tidak Ditemukan",
  "object": null
}
```

## Cara Menjalankan

Prasyarat: JDK 17.

```bash
git clone https://github.com/pikribahtiar/pengajuan-pinjaman-api.git
cd pengajuan-pinjaman-api
./mvnw spring-boot:run
```

Aplikasi berjalan di `http://localhost:8080`. Di Windows, gunakan `mvnw.cmd spring-boot:run`.

Menjalankan test:

```bash
./mvnw test
```

## Struktur Kode

| Class | Peran |
|---|---|
| `PengajuanPinjamanController` | Menerima request HTTP dan meneruskannya ke Service |
| `PengajuanPinjamanService` | Logika bisnis dan validasi |
| `PengajuanPinjaman` | Model data pengajuan |
| `ApiResponse` | Pembungkus response dengan format seragam |
| `GlobalExceptionHandler` | Mengubah exception menjadi response berformat `ApiResponse` |

## Catatan Desain

- **Controller dibuat tipis.** Logika bisnis dan validasi ada di Service, sehingga aturan hanya ditulis di satu tempat.
- **Penunjuk data memakai `id`, bukan `nama`.** Pada versi awal, data ditunjuk lewat nama sehingga dua pengajuan dengan nama yang sama bisa saling bertabrakan. Sekarang setiap data punya `id` UUID.
- **Format error seragam.** Service melempar `ResponseStatusException`, lalu `@RestControllerAdvice` mengubahnya menjadi `ApiResponse` sambil mempertahankan status HTTP yang sesuai.

## Keterbatasan dan Rencana Pengembangan

- Data disimpan di memori (`ArrayList`), sehingga hilang saat aplikasi di-restart.
- Error di luar `ResponseStatusException` (misalnya body JSON yang rusak) masih memakai format bawaan Spring.
- Rencana pengembangan:
  - Menyimpan data ke database dengan Spring Data JPA
  - Validasi input dengan Bean Validation (`@Valid`)
  - Unit test untuk `PengajuanPinjamanService`
  - Dokumentasi API dengan OpenAPI/Swagger