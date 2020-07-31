package cn.wolfcode.util;

import cn.wolfcode.domain.Employee;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserContext {

    /*public static final String EMPLOYEE_IN_SESSION="EMPLOYEE_IN_SESSION";
    public static final String EXPRESSION_IN_SESSION="EXPRESSION_IN_SESSION";

    //获取 session 对象
    public static HttpSession getSession(){
        //springmvc提供的工具，可以在任意地方获取到request对象，response对象， session对象
        //RequestContextHolder 持有上下文的Request容器
        ServletRequestAttributes attrs=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();//接收到请求，记录请求内容
        //获取请求request,并且通过 request获取session对象
        return attrs.getRequest().getSession();
    }

    //往session 存入登陆用户
    public static void setCurrentUser(Employee employee){
           getSession().setAttribute(EMPLOYEE_IN_SESSION, employee);
    }*/

    //通过session 获取登陆用户的信息
    public static Employee getCurrentUser(){
        //调用上面获取 session 对象的方法,再拿到当前session中 EMPLOYEE_IN_SESSION  的值
        //return (Employee) getSession().getAttribute(EMPLOYEE_IN_SESSION);

        return (Employee) SecurityUtils.getSubject().getPrincipal();
    }

    /*//往 session 存入/设置 登陆用户的权限信息
    public static void setExpression(List<String>permissions){
        getSession().setAttribute(EXPRESSION_IN_SESSION, permissions);
    }

    //从 session 中 获取登陆用户的权限信息
    public static List<String>getExpression(){
        //因为传进入的是一个 集合,那么拿出来就要用一个集合接受
        return (List<String>) getSession().getAttribute(EXPRESSION_IN_SESSION);
    }*/
}
