package com.bjy.neteasemusiccopy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bjy.neteasemusiccopy.config.NeteaseUrl;
import com.bjy.neteasemusiccopy.service.DownloadService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

@Service
@Slf4j
public class DownloadServiceImp implements DownloadService {

    @Resource
    NeteaseUrl neteaseUrl;

    @Resource
    RestTemplate restTemplate;

    @Resource
    String musicDir;

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

    @Override
    @SneakyThrows
    public ResponseEntity downloadMusic(String id,String fileName) {

        File musicFile = new File(musicDir+"/"+fileName+id);
        if (musicFile.exists()) {
            log.info("本地有歌曲:"+fileName);
        }
        else if (!musicFile.exists()){
            //如果本地没有歌曲,下载歌曲到本地
            musicFile.createNewFile();
            String getMusicUrl = neteaseUrl.getDownloadUrl()+"?id="+id;
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("user-agent", "Chrome/83.0.4103.116");
            HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);
            ResponseEntity<org.springframework.core.io.Resource> responseEntity = restTemplate.exchange(getMusicUrl, HttpMethod.GET, httpEntity, org.springframework.core.io.Resource.class);
            if (responseEntity.getBody()==null){
                musicFile.delete();
                throw new IOException("付费歌曲");
            }
            InputStream in = responseEntity.getBody().getInputStream();

            OutputStream outputStream = new FileOutputStream(musicFile);
            byte[] b = new byte[4096];
            while (in.read(b)!=-1){
                outputStream.write(b);
            }
            outputStream.flush();
            outputStream.close();
            in.close();
        }


        //解决中文乱码以及特殊符号的问题
        fileName = new String(fileName.getBytes("UTF8"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition","attachment; filename=" +  URLEncoder.encode(fileName + ".mp3","UTF8"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        log.info(String.valueOf(musicFile.length()));
        return ResponseEntity.ok().headers(headers).contentLength(musicFile.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(musicFile));
    }
}
