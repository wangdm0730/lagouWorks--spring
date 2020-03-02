package com.lagou.edu.utils;

import com.lagou.edu.aop.annotation.MyAutowired;
import com.lagou.edu.aop.annotation.MyService;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangdm
 * @description 客户端容器中各注解解析
 */
public class CustomAnnotationApplicationContext {

    private String basePackage;
    private static ConcurrentHashMap<String, Object> beanFactory = new ConcurrentHashMap<String, Object>();

    public CustomAnnotationApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        this.initBeans();
        Collection<Object> beans = beanFactory.values();
        for (Object bean : beans) {
            this.injectDependencies(bean);
        }
    }

    /**
     * 解析MyAutowired获取对象注入
     * @param bean
     */
    private void injectDependencies(Object bean) {
        Class<?> clz = bean.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            MyAutowired annotation = field.getAnnotation(MyAutowired.class);
            if (annotation != null) {
                //如果标注了@MyAutowired注解，则需要依赖注入
                Object obj = beanFactory.get(field.getName());
                field.setAccessible(true);//如果访问权限不够，需要设置此项,暴力访问类属性
                try {
                    field.set(bean, obj);   //依赖注入
                } catch (IllegalAccessException e) {   //设置了Accessible则不会抛此异常
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析MyService获取对象注入
     */
    public void initBeans() {
        List<Class<?>> classes = ClassUtils.getClasses(basePackage);
        for (Class<?> clz : classes) {
            MyService annotation = clz.getAnnotation(MyService.class);
            if (annotation != null) {
                // 标注了@MyService注解，需要纳入IoC容器管理
                Object bean = null;
                try {
                    bean = clz.newInstance();
                } catch (InstantiationException e) {
                    System.out.printf("实例化bean：%s 失败", clz.toString());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    System.out.printf("%s访问权限不够", clz.toString());
                    e.printStackTrace();
                }
                String beanId = this.toLowerCaseFirstChar(clz.getSimpleName());
                beanFactory.put(beanId, bean);
            }
        }
    }

    public Object getBean(String beanId) {
        return beanFactory.get(beanId);
    }

    /**
     * 统一小写字符（简化注入时输错）
     * @param className
     * @return
     */
    private String toLowerCaseFirstChar(String className) {
        StringBuilder stringBuilder = new StringBuilder(className.substring(0,1).toLowerCase());
        stringBuilder.append(className.substring(1));
        return stringBuilder.toString();
    }
}
