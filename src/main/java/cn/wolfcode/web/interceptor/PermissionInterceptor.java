/*package cn.wolfcode.web.interceptor;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.util.RequiredPermission;
import cn.wolfcode.util.UserContext;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取当前用户登陆信息,从session中获取当前用户信息
        Employee employee = UserContext.getCurrentUser();
        //2.判断是否超级管理员
        //isAdmin:是否管理员,能使用这个方法的前提是,该对象中有一个boolean类型的属性,相当于get方法
        if (employee.isAdmin()) {
            //如果是超级管理员,那么就放行
            return true;
        }
        //不是超级管理员,那么进入第三步
        //3.获取到当前要执行的处理方法(被拦截的方法)
        //判断是静态资源,直接放行,如果不这样子做的话就要在没有判断之前创建HandlerMethod给下面的删除权限的异步问题
        //instanceof 这个方法: 判断 handler 是否是 HandlerMethod 的子类或者实现类
        //handler :当前被拦截的方法对象
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        //如果不是静态资源,执行下面代码
        //HandlerMethod这个方法就是把方法封装成一个对象在访问请求方法的时候可以方便的访问到
        // 方法、方法参数、方法上的注解、所属类等并且对方法参数封装处理，也可以方便的访问到方法参数的注解等信息。
        HandlerMethod method = (HandlerMethod) handler;
        //4.判断是否有贴自定义注解
        //拿到当前被请求的方法注解
        RequiredPermission annotation = method.getMethodAnnotation(RequiredPermission.class);

        if (annotation==null){
            //如果当前方法没有 贴 RequiredPermission 注解 就是人人都可以访问,放行
            return true;
        }
        //该方法有贴注解就执行下面代码
        //5.获取方法的权限表达式   expression 表达式
        String expression = annotation.expression();

        //6.获取当前登录用户拥有的权限表达式集合,用于判断使用拥有当前方法的权限
        List<String> permissions = UserContext.getExpression();
        //7.判断用户拥有的权限表达式集合是否包含该方法的权限表达式
        if (permissions.contains(expression)){
            return true;//如果包含,那么就放行
        }
        //不包含,执行下面代码,跳转到错误页面
        //如果当前方法没有贴 ResponseBody 注解
        if (!method.hasMethodAnnotation(ResponseBody.class)){
            //如果该方法没有贴 ResponseBody 注解,那么就跳转界面
            request.getRequestDispatcher("/nopermission.do").forward(request,response );
        }else {
            //否则就是贴了 ResponseBody 注解,需要返回一个 JsonResult 对象
            JsonResult jsonResult = new JsonResult(false, "你好,你没有该操作的权限");
            //避免乱码问题
            response.setContentType("application/json;charset=utf-8");
            //把当前对象转换成 JSON 对象,并回应还回去浏览器
            response.getWriter().print(JSON.toJSONString(jsonResult));
        }
        return false;
    }
}*/
