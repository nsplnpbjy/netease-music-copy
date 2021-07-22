package com.bjy.neteasemusiccopy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author nsplnpbjy
 */
@Configuration
public class MusicDirConfig {

    @Bean
    public String musicDir(){
        File file = new File("./music");
        if (!file.exists()){
            file.mkdirs();
        }
        return "./music";
    }

}
