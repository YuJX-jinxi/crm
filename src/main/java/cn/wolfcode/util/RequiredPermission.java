package cn.wolfcode.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//为什么要使用注解
//因为权限问题,可以拦截到不同用户的访问,例如有些方法的访问是谁都可以访问的,那么针对这些谁都可以访问的方法,就可以不设置标签
//那些特殊权限才可以访问的方法,才要贴注解

@Target(ElementType.METHOD)//贴到方法上
@Retention(RetentionPolicy.RUNTIME)//运行时也可以使用注解
public @interface RequiredPermission {
    //给注解添加一些值

    String name();//权限名称

    String expression();//权限表达式
}
