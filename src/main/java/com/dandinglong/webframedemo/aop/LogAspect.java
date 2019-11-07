package com.dandinglong.webframedemo.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class LogAspect {
    private Logger logger=LoggerFactory.getLogger("controllerLogger");
    @Pointcut("execution(public * com.dandinglong.webframedemo.controller.*.*(..))")
    public void controllerPoint(){
    }
    @Before("controllerPoint()")
    public void doBefore(JoinPoint joinPoint){
        StringBuffer sb=new StringBuffer();
        sb.append("class:").append(joinPoint.getSignature().getDeclaringTypeName()).append("--Method:")
                .append(joinPoint.getSignature().getName()).append("--ArgsMap");
//        .append(JSON.toJSONString(joinPoint.getArgs()));
//        System.out.println("Before point cut");
//        System.out.println(joinPoint.getSignature());
//        System.out.println(joinPoint.getTarget());
//        System.out.println(joinPoint.getArgs());
        String signature = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        for(Object obj : args){
            if(obj instanceof HttpServletRequest){
                sb.append("requestMap").append(JSON.toJSONString(((HttpServletRequest) obj).getParameterMap()));
            }
        }
        logger.info(sb.toString());
    }
    @AfterThrowing(pointcut = "controllerPoint()",throwing = "e")
    public void afterThhrow(JoinPoint joinPoint, Throwable e){
        logger.error(e.getMessage());
    }
}
