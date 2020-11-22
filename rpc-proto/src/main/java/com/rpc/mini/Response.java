package com.rpc.mini;

import lombok.Data;

/**
 * @author xhzy
 */
@Data
public class Response<T> {

    private int code;

    private String message;

    private T data;

    public static <T> Response<T> success(T data){
        Response<T> response = new Response<>();
        response.setCode(200);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> fail(String message){
        Response<T> response = new Response<>();
        response.setCode(500);
        response.setMessage(message);
        return response;
    }
}
