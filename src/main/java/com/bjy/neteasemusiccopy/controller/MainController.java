package com.bjy.neteasemusiccopy.controller;

import com.bjy.neteasemusiccopy.config.SearchBody;
import com.bjy.neteasemusiccopy.config.StanderReturn;
import com.bjy.neteasemusiccopy.service.DownloadService;
import com.bjy.neteasemusiccopy.service.SearchService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author nsplnpbjy
 */
@RestController
public class MainController {

    @Resource
    SearchService searchService;

    @Resource
    DownloadService downloadService;

    @PostMapping("/search")
    public StanderReturn search(@RequestBody SearchBody searchBody){
        return new StanderReturn(searchService.doSearch(searchBody));
    }

    @GetMapping("/lyric")
    public StanderReturn lyric(@Param("id") String id){
        return new StanderReturn(downloadService.downloadLyric(id));
    }

}
