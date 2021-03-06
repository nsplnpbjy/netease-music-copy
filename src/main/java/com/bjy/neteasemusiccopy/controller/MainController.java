package com.bjy.neteasemusiccopy.controller;

import com.bjy.neteasemusiccopy.config.SearchBody;
import com.bjy.neteasemusiccopy.config.StanderReturn;
import com.bjy.neteasemusiccopy.service.CheckService;
import com.bjy.neteasemusiccopy.service.CommentsService;
import com.bjy.neteasemusiccopy.service.DownloadService;
import com.bjy.neteasemusiccopy.service.SearchService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URLDecoder;

/**
 * @author nsplnpbjy
 */
@RestController
@CrossOrigin
public class MainController {

    @Resource
    SearchService searchService;

    @Resource
    DownloadService downloadService;

    @Resource
    CheckService checkService;

    @Resource
    CommentsService commentsService;

    @PostMapping("/search")
    public StanderReturn search(@RequestBody SearchBody searchBody){
        return new StanderReturn(searchService.doSearch(searchBody));
    }

    @GetMapping("/isOk")
    public StanderReturn isOk(@Param("id") String id){
        return new StanderReturn(checkService.isOk(id));
    }

    @GetMapping("/music")
    public ResponseEntity music(@Param("id") String id, @Param("musicName") String musicName){
        return downloadService.downloadMusic(URLDecoder.decode(id),URLDecoder.decode(musicName));
    }

    @GetMapping("/lyric")
    public StanderReturn lyric(@Param("id") String id){
        return new StanderReturn(downloadService.downloadLyric(id));
    }

    @GetMapping("/comments")
    public StanderReturn comments(@Param("id") String id,@Param("limit") String limit,@Param("offset") String offset){
        return new StanderReturn(commentsService.getCommentsById(id,limit,offset));
    }

    @GetMapping("/mv")
    public ResponseEntity mv(@Param("id")String  id){
        return downloadService.downloadMv(id);
    }

    @GetMapping("/getTrueMvUrl")
    public String getTrueMvUrl(@Param("id")String  id){
        return downloadService.trueMvUrl(id);
    }

}
