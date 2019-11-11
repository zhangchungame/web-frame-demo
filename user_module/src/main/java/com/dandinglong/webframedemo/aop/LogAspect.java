package com.dandinglong.webframedemo.aop;

import com.alibaba.fastjson.JSON;
import com.dandinglong.webframedemo.entity.UserEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class LogAspect {
    private Logger logger=LoggerFactory.getLogger("ControllerLogger");
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
//    @AfterReturning(value = "controllerPoint()" ,returning = "object")
//    public void doAfter(JoinPoint joinPoint,Object object){
//        System.out.println(JSON.toJSONString(object));
//        if(object instanceof UserEntity){
//            ((UserEntity) object).setuName("改變了");
//        }
//    }
    @AfterThrowing(pointcut = "controllerPoint()",throwing = "e")
    public void afterThhrow(JoinPoint joinPoint, Throwable e){
        logger.error(e.getMessage());
    }
}
