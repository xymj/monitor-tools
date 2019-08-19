package com.monitor.zkmonitor.aop;

import com.monitor.zkmonitor.module.ZKSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: xymj
 * @Date: 2019/6/23 0023 13:11
 * @Description:
 */

@Slf4j
@Component
@Aspect
public class H2Aspect {

    @Pointcut("@annotation(com.monitor.zkmonitor.aop.H2Aop)")
    public void databaseOperatePointCut() {

    }

    @Around("databaseOperatePointCut()")
    public Object around(ProceedingJoinPoint point) {
        log.info("-----------------start point--------------------");
        long beginTime = System.currentTimeMillis();
        // 执行方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        System.out.println(signature.getMethod().getName());
        System.out.println(signature.getReturnType());
        System.out.println(signature.getDeclaringTypeName());
        String logPrefix = new StringBuilder(signature.getDeclaringTypeName())
                .append(".").append(signature.getMethod().getName()).toString();
        Object operate = null;
        try {
            operate = point.proceed();
            if (operate instanceof List) {
                if (CollectionUtils.isEmpty((List)operate)) {
                    operate = null;
                    log.error(logPrefix + ":" + "result is empty.");
                }
            } else if (operate instanceof Integer) {
                int row = (Integer) operate;
                if (row != 1) {
                    operate = null;
                    log.error(logPrefix + ":" + "operate row false.");
                }
            } else if (operate instanceof ZKSource) {
                if (operate == null) {
                    log.error(logPrefix + ":" + "result is null.");
                }
            }
        } catch (Throwable e) {
            log.error(logPrefix + ":", e);
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        log.info("use time:" + time);
        log.info("-----------------end point--------------------");

        if (operate != null) {
            log.info(logPrefix + ":" + " Operate sucess.");
        } else {
            log.info(logPrefix + ":" + " Operate fail.");
        }
        return operate;
    }

}
