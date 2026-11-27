package com.scw.scrowlifebackend.mojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class MyException extends RuntimeException {
    private final Integer code;      // 业务码
    private final String message;
    MyException(String message) {
        this.code = 400;
        this.message = message;
    }
}
