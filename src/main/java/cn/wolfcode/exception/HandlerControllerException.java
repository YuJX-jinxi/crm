package cn.wolfcode.exception;
import cn.wolfcode.qo.JsonResult;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 利用 aop 对控制器增强
 * 处理控制器出现大的异常
 */
@ControllerAdvice
public class HandlerControllerException {
    /**
     * @ExceptionHandler代表的方法是处理异常的.处理什么异常
     *
     * HandlerMethod 代表出现异常的处理方法
     * 它捕获到异常,就需要接收它,因为我们要解决异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Object handlerException(RuntimeException e, HttpServletResponse response, HandlerMethod method){
        //把异常信息打印出,方便我们开发的时候找bug
        e.printStackTrace();
        //判断有没有贴 ResponseBody 注解
        if (method.hasMethodAnnotation(ResponseBody.class)){
            String errorMsg="操作失败,请联系管理员";
            if (e instanceof LogicException){//判断是否逻辑异常,如果是,那就替换异常信息
                errorMsg=e.getMessage();
            }

        JsonResult json = new JsonResult(false, errorMsg);
        //避免乱码问题
        response.setContentType("application/json;charset=utf-8");
        try {
            //把当前对象转换成 JSON 对象,并回应还回去浏览器
            response.getWriter().print(JSON.toJSONString(json));
        } catch(Exception e1) {
            e1.printStackTrace();
        }
        return null;
        //如果不是,则返回错误视图
        }else {
            return "common/error";

        }
    }

    /**
     * 返回没有权限的信息
     * @param e
     * @param response
     * @param method
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Object handlerException(UnauthorizedException e, HttpServletResponse response, HandlerMethod method){
        //把异常信息打印出,方便我们开发的时候找bug
        e.printStackTrace();
        //判断有没有贴 ResponseBody 注解
        if (method.hasMethodAnnotation(ResponseBody.class)){
            String errorMsg="操作失败,请联系管理员";

            JsonResult json = new JsonResult(false, "你没有权限操作");
            //避免乱码问题
            response.setContentType("application/json;charset=utf-8");
            try {
                //把当前对象转换成 JSON 对象,并回应还回去浏览器
                response.getWriter().print(JSON.toJSONString(json));
            } catch(IOException e1) {
                e1.printStackTrace();
            }
            return null;
            //如果不是,则返回错误视图
        }else {
            return "common/nopermission";

        }
    }
}
