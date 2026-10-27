package com.latihan.pengajuan_pinjaman_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatus(ResponseStatusException ex) {
        ApiResponse<Void> body = new ApiResponse<>("01", ex.getReason(), null);
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }
}