package com.bjy.neteasemusiccopy.aspect;

import com.bjy.neteasemusiccopy.config.SearchBody;
import com.bjy.neteasemusiccopy.entity.SearchLog;
import com.bjy.neteasemusiccopy.service.ISearchLogService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "dataoption",name = "isEnable")
//控制数据库使用与否
public class DataOption {

    @Resource
    ISearchLogService iSearchLogService;

    @SneakyThrows
    @Around("execution(* com.bjy.neteasemusiccopy.service.impl.SearchServiceImp.doSearch(*))")
    public Object process(ProceedingJoinPoint proceedingJoinPoint){
        Object arg[] = proceedingJoinPoint.getArgs();
        SearchBody searchBody = (SearchBody) arg[0];
        Object returner = proceedingJoinPoint.proceed();
        log.info("搜索内容记录:"+searchBody.getS());
        iSearchLogService.save(new SearchLog(UUID.randomUUID().toString(),searchBody.getS(), LocalDateTime.now()));
        return returner;
    }

}
