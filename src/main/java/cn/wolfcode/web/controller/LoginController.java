package cn.wolfcode.web.controller;

import cn.wolfcode.exception.LogicException;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.service.IEmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private IEmployeeService employeeService;
    /*@Autowired
    private PermissionMapper permissionMapper;*/

    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(String username, String password){

        //获取登陆页面用户传进来的账号密码
        //能返回回来一个对象,那就是有对象,不然 在login 方法中已经判断账号密码是否正确了
           /* Employee employee = employeeService.login(username, password);
            //把员工信息放到 session 中带信息过去
            //不能使用 Model 作用域不同,转发一次就没了,只能当前页面,而session 只要不关闭浏览器都会存在,除非设置生命周期
            //session.setAttribute("EMPLOYEE_IN_SESSION", employee);
            if (employee.isStatus()){
            //把当前用户设置进去
            UserContext.setCurrentUser(employee);
            //判断当前用户是否超级管理员
            if (!employee.isAdmin()){
                //如果不是超级管理员,那么就获取当前用户的全部权限信息
                List<String> list = permissionMapper.selectExpressionByRmpId(employee.getId());
                //设置在 session 中设置权限信息
                UserContext.setExpression(list);
            }
            //证明他能登陆
            return new JsonResult();
            }else{
            return new JsonResult(false, "你老板把你号封了");
        }*/

    /*//退出登录的意思
    @RequestMapping("/logout")
    public String logout(HttpSession session){

        //消除会话
        session.invalidate();

        return "redirect:/login.html";
    }*/

        try {
            //获取 subject 主体
        Subject subject = SecurityUtils.getSubject();
        subject.getSession();

        //方式一,在封装 token 之前对密码加密,然后才传给 shiro 直接对比
        //对用户传进来的密码加密,然后再跟数据库做匹配
        Md5Hash md5Hash = new Md5Hash(password, username,2);
        //把前端传过来的参数封装为令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, md5Hash.toString());

        //方式二 : 登录时不需要自己加密,告诉 shiro 登录时要做加密,并且告诉 shiro 算法是怎么样的




        //使用 shiro 提供的 api 来登录 (必须要使用该方法,才会改变 subject 的认证状态)
        subject.login(token);
        return new JsonResult();
        //两个异常是规定的 ,是 shiro 判断用户名不正确就会报这个异常
        } catch(UnknownAccountException e) {
            e.printStackTrace();
            return new JsonResult(false,"用户名不存在");
        }catch(IncorrectCredentialsException e) {
            e.printStackTrace();
            return new JsonResult(false,"密码错误");
            // DisabledAccountException 内部的异常
        }catch(DisabledAccountException e) {
            e.printStackTrace();
            return new JsonResult(false,e.getMessage());
        }
    }


    //重置密码
    @RequestMapping("/updatePwd")
    public String updatePwd(){
        return "/passwordUpdate/updatePwd";
    }

    //管理员设置
    @RequestMapping("/resetPwd")
    public String resetPwd(Model model,Long id){
        //先通过id拿到当前员工的信息
        model.addAttribute("employee",employeeService.get(id));
        return "/passwordUpdate/resetPwd";
    }

    @RequestMapping("/nopermission")
    public String nopermission(){
        return "/common/nopermission";
    }

}
