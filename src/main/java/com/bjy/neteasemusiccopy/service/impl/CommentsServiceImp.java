package com.bjy.neteasemusiccopy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bjy.neteasemusiccopy.config.NeteaseUrl;
import com.bjy.neteasemusiccopy.service.CommentsService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommentsServiceImp implements CommentsService {

    @Resource
    NeteaseUrl neteaseUrl;

    @Resource
    RestTemplate restTemplate;

    @Override
    public JSONObject getCommentsById(String id,String limit,String offset) {
        String getUrl = neteaseUrl.getCommentsUrl()+id+"?limit={limit}&offset={offset}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("user-agent", "Chrome/83.0.4103.116");
        Map<String,String> map = new HashMap<>(2);
        map.put("limit",limit);
        map.put("offset",offset);
        JSONObject jsonObject = restTemplate.getForObject(getUrl,JSONObject.class,map);
        return jsonObject;
    }
}
