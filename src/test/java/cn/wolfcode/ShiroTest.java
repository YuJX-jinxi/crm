package cn.wolfcode;

import cn.wolfcode.shiro.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class ShiroTest {

    @Test
    public void test(){
        //安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //数据源(相当于 dao层),提供数据给 shiro 进行判断
        //IniRealm realm = new IniRealm("classpath:shiro.ini");

        //设置数据源到安全管理器中
        //securityManager.setRealm(realm);
        //设置数据源到安全管理器中(使用自定义的数据源)
        securityManager.setRealm(new MyRealm());

        //把安全管理器设置到当前的环境中使用
        SecurityUtils.setSecurityManager(securityManager);

        //获取主体(访问系统到的用户,无论有没有登陆过都能获取到主体对象,但是状态不同)
        Subject subject = SecurityUtils.getSubject();
        System.out.println("认证前的状态" + subject.isAuthenticated());//认证前的状态false

        //使用 shiro 来进行认证 ( 登陆/退出 )

        //创建令牌 ( 来源: 来自登陆页面的表单)
       // UsernamePasswordToken token = new UsernamePasswordToken("lisi","666");
        UsernamePasswordToken token = new UsernamePasswordToken("赵总","1");

        //IncorrectCredentialsException : 错误的认证信息,知道账号存在,密码错误
        //UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","5555");

        //UnknownAccountException  :  未知的账号错误  账号不存在的错误
        //UsernamePasswordToken token = new UsernamePasswordToken("zhangsankl","555");

        subject.login(token);//需要传入的参数 AuthenticationToken 用户令牌(网页传进来的账号密码)
        System.out.println("认证后的状态"+subject.isAuthenticated());//认证后的状态true

        //退出登录/注销登录
       /* subject.logout();
        System.out.println("退出后的状态"+subject.isAuthenticated());//退出后的状态false*/


       //判断用户是否有 HR 的角色
        //只要拿到 subject 就可以做到很多事情,为所欲为
        //判断当前 用户 lisi  有没有 权限
        System.out.println("hr:" + subject.hasRole("hr"));//hr:false
        System.out.println("seller:" + subject.hasRole("seller"));//seller:true

        //check 开头的,是没有返回值,如果没有这个角色,会直接抛异常,程序中断
        //subject.checkRole("hr");   //UnauthorizedException  :没有当前权限的 异常


        //判断用户是否有 user:delete 权限
        System.out.println("user:delete:" + subject.isPermitted("user:delete"));//user:delete:false
        System.out.println("user:delete:" + subject.isPermitted("user:update"));//user:delete:true

        //check 会直接中断程序
        subject.checkPermission("user:delete");  //UnauthorizedException 未经授权的异常
    }


    @Test
    public void testMD5(){
        Md5Hash md5Hash = new Md5Hash("1","admin",3);
        System.out.println(md5Hash);
    }
}
