package com.scw.scrowlifebackend.mojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
    private Integer code;
    private String message;
    private Object data;

    public static Response success(Object data) {
        return new Response(200, "success", data);
    }
    public static Response error( String message) {
        return new Response(400, message, null);
    }
    public static Response error(Integer code, String message) {
        return new Response(code, message, null);
    }
}
