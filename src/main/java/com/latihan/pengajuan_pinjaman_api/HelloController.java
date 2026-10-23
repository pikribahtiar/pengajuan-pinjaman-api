package com.latihan.pengajuan_pinjaman_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController itu "label" yang bilang ke Spring:
// "class ini isinya endpoint-endpoint REST API, bukan class biasa"
// Mirip konsepnya kayak @Route di Vaadin kemarin, cuma beda konteks:
// @Route -> "class ini adalah halaman web"
// @RestController -> "class ini adalah kumpulan endpoint API"
@RestController
public class HelloController {

    // @GetMapping("/hello") itu bilang ke Spring:
    // "kalau ada request HTTP GET ke alamat /hello, jalanin method ini"
    // Ini pengganti manual bikin HttpServlet + override method doGet()
    // yang biasanya ribet di Java lama.
    @GetMapping("/hello")
    public String sayHello() {
        // Apapun yang di-return method ini, itu yang jadi RESPONSE
        // yang dikirim balik ke browser/Postman yang minta.
        return "Halo dari Spring Boot!";
    }
}