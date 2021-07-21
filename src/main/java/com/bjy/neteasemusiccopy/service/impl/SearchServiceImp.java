package com.bjy.neteasemusiccopy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bjy.neteasemusiccopy.config.NeteaseUrl;
import com.bjy.neteasemusiccopy.config.SearchBody;
import com.bjy.neteasemusiccopy.entity.SearchLog;
import com.bjy.neteasemusiccopy.service.ISearchLogService;
import com.bjy.neteasemusiccopy.service.SearchService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author nsplnpbjy
 */
@Service
public class SearchServiceImp implements SearchService {

    @Resource
    ISearchLogService iSearchLogService;

    @Resource
    NeteaseUrl neteaseUrl;

    @Resource
    RestTemplate restTemplate;

    @Override
    public JSONObject doSearch(SearchBody searchBody) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("user-agent", "Chrome/83.0.4103.116");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("s",searchBody.getS());
        postParameters.add("offset",searchBody.getOffset());
        postParameters.add("limit",searchBody.getLimit());
        postParameters.add("type",searchBody.getType());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, httpHeaders);
        JSONObject response = restTemplate.postForObject(neteaseUrl.getSearchUrl(),httpEntity, JSONObject.class);
        iSearchLogService.save(new SearchLog(UUID.randomUUID().toString(),searchBody.getS(),LocalDateTime.now()));
        return response;
    }
}
