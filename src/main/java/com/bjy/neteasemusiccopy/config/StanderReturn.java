package com.bjy.neteasemusiccopy.config;

import lombok.Data;

/**
 * @author nsplnpbjy
 */
@Data
public class StanderReturn <T>{
    private int code;
    private String msg;
    private String dataName;
    private T data;

    public StanderReturn(T data){
        this.code = 0;
        this.msg = "ok";
        this.data = data;
    }

    public StanderReturn(T data,String dataName){
        this.code = 0;
        this.msg = "ok";
        this.data = data;
        this.dataName = dataName;
    }

    public StanderReturn(T data,int code,String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
