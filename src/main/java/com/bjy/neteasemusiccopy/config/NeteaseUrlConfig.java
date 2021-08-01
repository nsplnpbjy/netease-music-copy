package com.bjy.neteasemusiccopy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nsplnpbjy
 */
@Configuration
public class NeteaseUrlConfig {

    @Value("${netease.searchurl}")
    private String searchUrl;
    @Value("${netease.downloadUrl}")
    private String downloadUrl;
    @Value("${netease.lyricUrl}")
    private String lyricUrl;
    @Value("${netease.commentsUrl}")
    private String commentsUrl;
    @Bean
    NeteaseUrl neteaseUrl(){
        NeteaseUrl neteaseUrl = new NeteaseUrl();
        neteaseUrl.setSearchUrl(searchUrl);
        neteaseUrl.setDownloadUrl(downloadUrl);
        neteaseUrl.setLyricUrl(lyricUrl);
        neteaseUrl.setCommentsUrl(commentsUrl);
        return neteaseUrl;
    }
}
