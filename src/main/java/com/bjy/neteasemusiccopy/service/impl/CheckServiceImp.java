package com.bjy.neteasemusiccopy.service.impl;

import com.bjy.neteasemusiccopy.config.NeteaseUrl;
import com.bjy.neteasemusiccopy.service.CheckService;
import com.bjy.neteasemusiccopy.service.DownloadService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class CheckServiceImp implements CheckService {

    @Resource
    NeteaseUrl neteaseUrl;

    @Resource
    RestTemplate restTemplate;

    @Override
    public String isOk(String id) {
        String getMusicUrl = neteaseUrl.getDownloadUrl()+"?id="+id;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("user-agent", "Chrome/83.0.4103.116");
        HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);
        ResponseEntity<org.springframework.core.io.Resource> responseEntity = restTemplate.exchange(getMusicUrl, HttpMethod.GET, httpEntity, org.springframework.core.io.Resource.class);
        if (responseEntity.getBody()==null){
            return "no";
        }
        return "ok";
    }
}
