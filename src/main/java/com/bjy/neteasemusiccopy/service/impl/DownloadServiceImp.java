package com.bjy.neteasemusiccopy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bjy.neteasemusiccopy.config.MvUrlAndQuality;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private MvUrlAndQuality getTrueUrl(String id) throws IOException {
        MvUrlAndQuality mvUrlAndQuality = null;
        String url = neteaseUrl.getGetMvUrl();
        url = url+"?id="+id+"&type=mp4";
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList<>();
        cookies.add("_ntes_nnid=150b871105d67e01cf4ccc6238ea16d6,1617939375239");
        cookies.add("_ntes_nuid=150b871105d67e01cf4ccc6238ea16d6");
        cookies.add(" _iuqxldmzr_=32");
        cookies.add("NMTID=00OxgZbX0uVtOqSmUeFnkFrrKcbywAAAAF6w29Jmg");
        cookies.add("WEVNSM=1.0.0");
        cookies.add("WNMCID=oyhggl.1626776488904.01.0");
        cookies.add("WM_TID=XNuN3DjEYQ1BAUQREFIuyKVaKL9w80c9");
        cookies.add("WM_NI=r7FQrnX2x4lZX1lcY7fF5cVT113cCgc7ePwjfIuJq9RlR3rS8BRKRyNHh3Hxq3V7mDfE7S0BWvYpi3VDWAc87nV1xavyk%2FInEGYUE1XGcE2vUmFMBq%2FkRpRDHpgIHHGZczM%3D");
        cookies.add(" WM_NIKE=9ca17ae2e6ffcda170e2e6ee8eca478eb0a098e73ae9928fb2d44a969b8f85f53a8db08883f1218994bd98ed2af0fea7c3b92ab2e98ab2f1669ceeb8d5ef529cefa484dc3da69afb84d560f3effd84c26da1e9abb1f662a697bbabf43392b49ed4f159fc9e9eacd1738e98a8d4cb74b3ed9ad8f050a69bf9b4b34a95aa9791ec39969c99a2e234f5b29ad2fb6bb6a7a5d0cc66879997b6c83b9c99ada6b17e97b38aaec17bedb59e91d1508cb0a9aacb448586ad9bd037e2a3");
        cookies.add(" JSESSIONID-WYYY=1duO8RcCyv45CuC0U9C8%2F5cpmPZm503UX0hiDs6F51Arll5POXsH4fdmPiOswMZs3YDX7Am3BQP9kTFPzhhyOkdiqwG9WYR7thZ%2Fx%5CN9Ef6vH%2BKRTwfeftDF%2FWmNHZRwaX6gZ%2BVqEn4ijyVhZbITXc1V0xMBdFr1RIluTIEqgEaeCxfs%3A1627560937169");

        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36");
        headers.put(HttpHeaders.COOKIE,cookies);
        HttpEntity<String> entity = new HttpEntity(headers);
        ResponseEntity<String> res = restTemplate.exchange(url,HttpMethod.GET,entity,String.class);
        JSONObject info = JSONObject.parseObject(res.getBody());
        if (info.isEmpty()){
            throw new IOException("请求MV出错，可能是网易服务器忙");
        }
        else if (!info.containsKey("data")){
            throw new IOException("返回数据不足，可能是网易服务器忙");
        }
        JSONObject urls = info.getJSONObject("data").getJSONObject("brs");
        if (urls.isEmpty()){
            throw new IOException("此歌曲MV为空");
        }
        if (urls.containsKey("720")){
            mvUrlAndQuality = new MvUrlAndQuality(urls.getString("720"),720);
        }
        else  if (urls.containsKey("1080")){
            mvUrlAndQuality = new MvUrlAndQuality(urls.getString("1080"),1080);
        }
        else  if (urls.containsKey("480")){
            mvUrlAndQuality = new MvUrlAndQuality(urls.getString("480"),480);
        }
        else  if (urls.containsKey("240")){
            mvUrlAndQuality = new MvUrlAndQuality(urls.getString("240"),240);
        }
        return mvUrlAndQuality;
    }

    @SneakyThrows
    @Override
    public ResponseEntity downloadMv(String id) {
        int quality = 0;
        String mvPlayUrl = null;
        MvUrlAndQuality mvUrlAndQuality = getTrueUrl(id);
        mvPlayUrl = mvUrlAndQuality.getMvPlayUrl();
        quality = mvUrlAndQuality.getQuality();
        File mvFile = new File(musicDir+"/"+id+quality+".mp4");
        if (!mvFile.exists()){
            mvFile.createNewFile();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36");
            HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);
            ResponseEntity<org.springframework.core.io.Resource> responseEntity = restTemplate.exchange(mvPlayUrl, HttpMethod.GET, httpEntity, org.springframework.core.io.Resource.class);
            if (responseEntity.getBody()==null){
                mvFile.delete();
                throw new IOException("获取失败");
            }
            InputStream in = responseEntity.getBody().getInputStream();
            FileOutputStream out = new FileOutputStream(mvFile);
            byte[] buffer = new byte[4096];
            while (in.read(buffer)!=-1){
                out.write(buffer);
            }
            out.flush();
            out.close();
            in.close();
        }

        //解决中文乱码以及特殊符号的问题
        String fileName = mvFile.getName();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition","attachment; filename=" +  URLEncoder.encode(fileName ,"UTF8"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        log.info(String.valueOf(mvFile.length()));
        return ResponseEntity.ok().headers(headers).contentLength(mvFile.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(mvFile));
    }

    @SneakyThrows
    @Override
    public String trueMvUrl(String id) {
        MvUrlAndQuality mvUrlAndQuality = getTrueUrl(id);
        if (mvUrlAndQuality.getMvPlayUrl().isEmpty()){
            throw new IOException("获取真实地址错误");
        }
        return mvUrlAndQuality.getMvPlayUrl();
    }
}
