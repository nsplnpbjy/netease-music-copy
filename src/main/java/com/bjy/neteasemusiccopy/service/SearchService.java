package com.bjy.neteasemusiccopy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjy.neteasemusiccopy.config.SearchBody;

/**
 * @author nsplnpbjy
 */
public interface SearchService {
    /**搜索**/
    public JSONObject doSearch(SearchBody searchBody);
}
