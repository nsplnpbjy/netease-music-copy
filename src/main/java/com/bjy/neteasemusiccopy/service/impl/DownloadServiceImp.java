package com.bjy.neteasemusiccopy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bjy.neteasemusiccopy.config.NeteaseUrl;
import com.bjy.neteasemusiccopy.service.DownloadService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class DownloadServiceImp implements DownloadService {

    @Resource
    NeteaseUrl neteaseUrl;

    @Resource
    RestTemplate restTemplate;

    @Override
    public JSONObject downloadLyric(String id) {
        String getLyricUrl = neteaseUrl.getLyricUrl()+"?id="+id;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("user-agent", "Chrome/83.0.4103.116");
        HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getLyricUrl, HttpMethod.GET, httpEntity,String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        return jsonObject;
    }
}
