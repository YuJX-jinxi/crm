/*
package cn.wolfcode.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    //Object handler:因为不知道传进来的是什么方法,或者不知道什么资源
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否有登陆
        HttpSession session = request.getSession();
        //获取session中查询得到的 对象信息
        Object employee = session.getAttribute("EMPLOYEE_IN_SESSION");

        //判断是否超级管理员,如果不是就查询
        //如果有登陆就放行,没有就跳转到登陆页面(不放行)
        if (employee!=null){
            //固定写法
            return true;
        }
        //不放行就跳转到登陆界面
        response.sendRedirect("/login.html");
        return false;
    }
}
*/
