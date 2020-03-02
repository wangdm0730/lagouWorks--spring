package com.lagou.edu.aop;

import com.lagou.edu.aop.annotation.MyAutowired;
import com.lagou.edu.aop.annotation.MyService;
import com.lagou.edu.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

/**
 * @author wangdm
 * 自定义事务注解切面逻辑实现(用切面方式来玩玩..)
 */
@Aspect
@MyService
public class MyTransationalAop {

    @MyAutowired
    private TransactionManager transactionManager;

    @Around("@annotation(com.lagou.edu.aop.annotation.MyTransational)")
    public Object doTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object o = null;
        try{
            // 开启事务(关闭事务的自动提交)
            transactionManager.beginTransaction();
            o = proceedingJoinPoint.proceed();
            // 提交事务
            transactionManager.commit();
        }catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            transactionManager.rollback();
            // 抛出异常便于上层servlet捕获
            throw e;
        }
        return o;
    }
}
