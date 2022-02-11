package com.bjy.neteasemusiccopy.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjy.neteasemusiccopy.config.NeteaseUrl;
import com.bjy.neteasemusiccopy.config.SearchBody;
import com.bjy.neteasemusiccopy.service.SearchService;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nsplnpbjy
 */
@Service
public class SearchServiceImp implements SearchService {

    @Resource
    NeteaseUrl neteaseUrl;

    @Resource
    RestTemplate restTemplate;

    @Override
    public JSONObject doSearch(SearchBody searchBody) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("user-agent", "Chrome/83.0.4103.116");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.set("Cookie", "NMTID=00Ov9Q5zjZwSYJKQ07QhS_ikAAYL4IAAAF-6fET0w");
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("s",searchBody.getS());
        postParameters.add("offset",searchBody.getOffset());
        postParameters.add("limit",searchBody.getLimit());
        postParameters.add("type",searchBody.getType());
        //HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, httpHeaders);
        //JSONObject response = restTemplate.postForObject(neteaseUrl.getSearchUrl(),httpEntity, JSONObject.class);
        String getUrl = neteaseUrl.getSearchUrl()+"?s="+searchBody.getS()+"&offset="+searchBody.getOffset()+"&limit="+searchBody.getLimit()+"&type="+searchBody.getType();
        HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, JSONObject.class);
        return responseEntity.getBody();
    }
}
