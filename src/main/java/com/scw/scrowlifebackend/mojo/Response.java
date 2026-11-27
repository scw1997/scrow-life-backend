package com.scw.scrowlifebackend.mojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Data
public class Response {
    private Integer code;
    private String msg;
    private Object data;

    public static Response success(Object data) {
        return new Response(200, "success", data);
    }
    public static Response error( String msg) {
        return new Response(400, msg, null);
    }
    public static Response error(Integer code, String msg) {
        List<Integer> list = Arrays.asList(1, 2, 3);
        return new Response(code, msg, null);
    }
}
