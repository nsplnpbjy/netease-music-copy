package com.bjy.neteasemusiccopy.controller;

import com.bjy.neteasemusiccopy.config.StanderReturn;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public StanderReturn ioexception(IOException e){
        if (e.getMessage().equals("返回数据不足")){
            return new StanderReturn(null,10001,"no source");
        }
        return new StanderReturn(null,400,"error");
    }
}
