package com.Aspect;

import com.DeviceTask.ChangeStatusPojos;
import com.DeviceTask.TaskService;
import com.Redis.RedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
//
//@Aspect
//@Component
public class RedisAspectForTask {
//
//    @Pointcut("execution(public * com.util.timingService.reportCurrentTime(..))")
//    public void RedisAspectForTask() {
//    }
//
//    //@Before 在方法前执行
//    @Transactional
//    @Before("RedisAspectForTask()")
//    public void doBefore(JoinPoint joinPoint)
//    {
//
//
//    }
//
//    //@After 在方法后执行
//    @After("RedisAspectForTask()")
//    public void doAfter(JoinPoint joinPoint)
//    {
//        System.out.println("After");
//    }
//
//    //@AfterReturning 在方法执行后返回一个结果后执行
//    @AfterReturning("RedisAspectForTask()")
//    public void doAfterRunning(JoinPoint joinPoint)
//    {
//        System.out.println("doAfterRunning");
//    }
//
//    //@AfterThrowing 在方法执行过程中抛出异常的时候执行
//    @AfterThrowing("RedisAspectForTask()")
//    public void doAfterThrowing(JoinPoint joinPoint)
//    {
//        System.out.println("AfterThrowing");
//    }
//
//    //@Around 环绕通知，就是可以在执行前后都使用，
//    //这个方法参数必须为ProceedingJoinPoint，proceed()方法就是被切面的方法，
//    // 上面四个方法可以使用JoinPoint，JoinPoint包含了类名，被切面的方法名，参数等信息。
//    @Around("RedisAspectForTask()")
//    public Object deAround(ProceedingJoinPoint joinPoint) throws Throwable{
//        System.out.println("deAround");
//        return joinPoint.proceed();
//    }
}