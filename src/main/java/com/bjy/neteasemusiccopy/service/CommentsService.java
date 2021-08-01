package com.bjy.neteasemusiccopy.service;

import com.alibaba.fastjson.JSONObject;

public interface CommentsService {
    public JSONObject getCommentsById(String id,String limit,String offset);
}
