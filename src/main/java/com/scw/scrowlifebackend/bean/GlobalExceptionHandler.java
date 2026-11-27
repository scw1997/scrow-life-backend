package com.scw.scrowlifebackend.bean;


import com.scw.scrowlifebackend.mojo.MyException;
import com.scw.scrowlifebackend.mojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理 @Valid接口字段参数校验失败,通常默认情况下自动返回400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //这里覆盖设置依然返回200状态码
    @ResponseStatus(HttpStatus.OK)
    public Response handleValidation(
            MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e ->
                        e.getField() + " " +
                        e.getDefaultMessage() // 校验消息
                )
                //map后通过collect将stream转换成集合后再通过join连接成字符串
                .collect(Collectors.joining(", "));

        log.warn("参数校验失败: {}", errors);
        //设置报错时返回数据格式
        return Response.error(errors.toString());
    }

    // service层逻辑判断后自主抛出的异常
    @ExceptionHandler(MyException.class)
    public Response handleMyException(MyException e) {
        log.error("MyException抛出异常", e);  // 完整日志进服务端
        return  Response.error(e.getCode(),e.getMessage());
    }


    // 兜底：未预期异常 → 500，不暴露堆栈
    @ExceptionHandler(Exception.class)
    public Response handleUnknown(Exception e) {
        log.error("未预期异常", e);  // 完整日志进服务端
        return  Response.error(500,e.getMessage());
    }



}