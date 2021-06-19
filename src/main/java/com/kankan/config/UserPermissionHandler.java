package com.kankan.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Lazy
public class UserPermissionHandler {

  @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)||"
      + "@annotation(org.springframework.web.bind.annotation.PostMapping)")
  public void request() {
  }

  @Around("request()")
  public Object userTokenCheck(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    log.info("收到请求，target ={},method={},args={}", joinPoint.getTarget().toString()
        , methodSignature.getName(), joinPoint.getArgs());
    Object result = joinPoint.proceed(joinPoint.getArgs());
    log.info("收到result，target ={},method={},args={},result={}", joinPoint.getTarget().toString()
        , methodSignature.getName(), joinPoint.getArgs(), result);
    return result;
  }

}
