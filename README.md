# Pengajuan Pinjaman API

REST API sederhana untuk mengelola data pengajuan pinjaman, dibuat dengan Spring Boot. Proyek latihan untuk mendalami pembuatan REST API: operasi CRUD, validasi input, dan penggunaan status HTTP.

## Teknologi

- Java 17
- Spring Boot 4.1.1 (Spring Web MVC)
- Maven (termasuk Maven Wrapper, tidak perlu install Maven terpisah)

## Fitur

- CRUD data pengajuan pinjaman (data disimpan di memori)
- Setiap pengajuan memiliki `id` unik (UUID) yang dibuat oleh server
- Validasi input: nama wajib diisi dan jumlah pinjaman minimal Rp100.000
- Input tidak valid ditolak dengan status HTTP `400 Bad Request`

## Endpoint

| Method | URL | Fungsi |
|---|---|---|
| POST | `/pengajuan/simpan` | Menyimpan pengajuan baru |
| GET | `/pengajuan/semua` | Menampilkan semua pengajuan |
| PUT | `/pengajuan/ubah/{id}` | Mengubah nama dan jumlah pinjaman |
| DELETE | `/pengajuan/hapus/{id}` | Menghapus pengajuan |

### Contoh: menyimpan pengajuan

**Request**

```
POST /pengajuan/simpan
Content-Type: application/json
```

```json
{
  "nama": "Budi",
  "jumlahPinjaman": 5000000
}
```

**Response sukses** (HTTP 200)

```json
{
  "id": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "nama": "Budi",
  "jumlahPinjaman": 5000000.0
}
```

**Response gagal** (HTTP 400), misalnya jumlah pinjaman di bawah Rp100.000

```json
{
  "timestamp": "2026-10-23T10:55:04.986Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Data Pengajuan Tidak Valid",
  "path": "/pengajuan/simpan"
}
```
### Contoh: mengubah pengajuan

**Request**

```
PUT /pengajuan/ubah/eac5e446-c516-49d7-8d7c-5396a3bb7621
Content-Type: application/json
```

```json
{
  "nama": "Budi",
  "jumlahPinjaman": 9000000
}
```
**Response sukses** (HTTP 200)

```json
true
```
**Response gagal** (HTTP 200)

```json
false
```
### Contoh: menghapus pengajuan

**Request**

```
PUT /pengajuan/hapus/eac5e446-c516-49d7-8d7c-5396a3bb7621
Content-Type: application/json
```

**Response sukses** (HTTP 200)

```json
true
```
**Response gagal** (HTTP 200)

```json
false
```
<!-- VERIFIKASI: kirim POST sukses dan POST dengan jumlahPinjaman 50000 di Postman, lalu samakan kedua contoh response di atas (termasuk status code dan ada tidaknya field "message") dengan hasil aslinya. -->
<!-- TODO: tambahkan contoh request dan response untuk GET, PUT, dan DELETE setelah dicek di Postman. -->

## Cara Menjalankan

Prasyarat: JDK 17.

```bash
git clone https://github.com/pikribahtiar/pengajuan-pinjaman-api
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

## Catatan Desain

- **Controller dibuat tipis.** Logika bisnis dan validasi ada di Service, sehingga aturan hanya ditulis di satu tempat.
- **Penunjuk data memakai `id`, bukan `nama`.** Pada versi awal, data ditunjuk lewat nama sehingga dua pengajuan dengan nama yang sama bisa saling bertabrakan. Sekarang setiap data punya `id` UUID.
- **Validasi lewat `ResponseStatusException`.** Service melempar exception saat input tidak valid, dan Spring mengubahnya menjadi response dengan status `400`. Format body error masih mengikuti format bawaan Spring.

## Keterbatasan dan Rencana Pengembangan

- Data disimpan di memori (`ArrayList`), sehingga hilang saat aplikasi di-restart.
- Rencana pengembangan:
    - Menyimpan data ke database dengan Spring Data JPA
    - Validasi input dengan Bean Validation (`@Valid`)
    - Format response error yang seragam dengan `@RestControllerAdvice`
    - Unit test untuk `PengajuanPinjamanService`
    - Dokumentasi API dengan OpenAPI/Swagger
