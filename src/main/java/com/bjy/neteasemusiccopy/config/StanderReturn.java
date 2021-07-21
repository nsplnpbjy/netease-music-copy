package com.bjy.neteasemusiccopy.config;

import lombok.Data;

/**
 * @author nsplnpbjy
 */
@Data
public class StanderReturn <T>{
    private int code;
    private String msg;
    private T data;

    public StanderReturn(T data){
        this.code = 0;
        this.msg = "ok";
        this.data = data;
    }
}
