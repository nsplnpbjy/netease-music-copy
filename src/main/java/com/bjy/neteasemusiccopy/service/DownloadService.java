package com.bjy.neteasemusiccopy.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;

import java.io.File;

/**
 * @author nsplnpbjy
 */
public interface DownloadService {
    public JSONObject downloadLyric(String id);

    public ResponseEntity downloadMusic(String id, String fileName);
}
