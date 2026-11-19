package com.scw.scrowlifebackend.bean;


import com.scw.scrowlifebackend.mojo.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @Data
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String message;
        private Object rejectedValue;
    }


    // 处理 @Valid接口字段参数校验失败,通常默认情况下自动返回400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //这里覆盖设置依然返回200状态码
    @ResponseStatus(HttpStatus.OK)
    public Response handleValidation(
            MethodArgumentNotValidException ex) {

        List<FieldError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new FieldError(
                        e.getField(),           // 字段名: "address.city"
                        e.getDefaultMessage(),  // 校验消息
                        e.getRejectedValue()    // 非法值（可选）
                ))
                .collect(Collectors.toList());

        log.warn("参数校验失败: {}", errors);
        //设置报错时返回数据格式
        return Response.error(errors.toString());
    }




}