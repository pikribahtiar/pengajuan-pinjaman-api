package com.latihan.pengajuan_pinjaman_api;

public class ApiResponse<T> {
    private String errorCode;
    private String errorMessage;
    private T object;

    public ApiResponse(String errorCode, String errorMessage, T object) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.object = object;
    }

    // Jalan pintas biar Controller gak perlu nulis "00" dan "Success" berulang
    public static <T> ApiResponse<T> success(T object) {
        return new ApiResponse<>("00", "Success", object);
    }

    public String getErrorCode() { return errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public T getObject() { return object; }
}