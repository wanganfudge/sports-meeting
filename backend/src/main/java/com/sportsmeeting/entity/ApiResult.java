package com.sportsmeeting.entity;
import lombok.Data;
@Data
public class ApiResult<T> {
    private int code;
    private String msg;
    private T data;
    public static <T> ApiResult<T> ok(T data) {
        ApiResult<T> r = new ApiResult<>();
        r.code = 200; r.msg = "success"; r.data = data;
        return r;
    }
    public static <T> ApiResult<T> fail(String msg) {
        ApiResult<T> r = new ApiResult<>();
        r.code = 500; r.msg = msg; r.data = null;
        return r;
    }
}