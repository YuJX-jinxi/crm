package cn.wolfcode.shiro;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.exception.LogicException;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.service.IRoleService;
import cn.wolfcode.util.UserContext;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义数据源
 * 因为数据库是我们只提供的,所以要我们创建这个类,要AuthorizingRealm 这个类去帮我们去做判断
 */
@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRoleService roleService;

    /*@Autowired
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }*/
    /**
     * 提供授权信息的一个方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //没使用缓存,每次都需要进入这里,每次访问都会进入六七次
        //用了缓存只用一次
        System.out.println("------------------------------------");

        //在这里提供当前登陆用户登录权限的数据

        //1.获取当前登录的员工的对象,获取员工id
        //员工是提供认证信息下面给的,现在只是拿
        Employee currentUser = UserContext.getCurrentUser();
        Long employeeId = currentUser.getId();

        /*方法二
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        Long employeeId = employee.getId();*/

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //2.查询数据库该员工的拥有的角色和权限数据
        //3.设置当前登录用户拥有的角色和权限数据
        if (!currentUser.isAdmin()){//判断是否超级管理员
            //通过员工id,查询员工的权限信息
            List<String>expressions=permissionService.selectExpressionByEmpId(employeeId);
            info.addStringPermissions(expressions);//设置权限
            //通过员工的id,查询员工拥有的角色
            List<String>sns=roleService.selectSnByEmpId(employeeId);
            info.addRoles(sns);
        }else{
            //权限拦截的功能是 shiro 来做的,不知道 Employee 的属性的意义,它还是按照权限表达式来判断的,可以给通配符
            info.addStringPermission("*:*");//通配符
            info.addRole("admin");//管理员角色
        }
        /*返回的仅仅只是当前用户的登陆信息,和用户的权限功能*/
        return info;
    }


    /**
     * 提供认证信息的一个方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //当前的方法如果直接返回一个 null ,那么就是 用户名不存在,那么 shiro 会自动抛出账号不存在的异常
        //如果当前用户不为空 , shiro 会自动从返回的对象中获取到真实的密码,再与 token 去对比


        //1.获取令牌中的用户名
        //拿到用户名的方法一
       /* UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)token;
        //拿到用户名
        usernamePasswordToken.getUsername();*/

       //拿到用户名的方法二
        //前端传来的信息
        String username = (String)token.getPrincipal();

        //2.查询数据库中是否存在该员工(模拟数据库中的假数据)
        Employee employee = employeeService.selectByName(username);

        //3.如果不存在,直接返回 null
        if (employee!=null){
            //if (employee.getStatue()){
            /*if (!false){ //如果直接抛出 shiro 提供的异常,它不会被封装,如果抛自定义的异常,就会被 shiro 封装成它自己的异常
                throw new DisabledAccountException("账号被禁用");
            }*/

            //4.如果存在,返回  SimpleAuthenticationInfo 对象
            //身份信息可以在任意地方获取的  用来跟subject 绑定在一起
            //在项目中时,就直接传入员工对象,代表跟 subject 绑定在一起,这样方便我们后续在任意地方获取当前员工对象
            //第一个空输入 用户名/主体 ,第二个输入凭证信息(真实的密码,第三个空传入当前数据源的名字,this.getName()即可)
            //第三个空对于我们当前的项目没有作用,一般是一个项目中有多个数据源是,需要做标志,代表数据是从哪个数据源中查询
            return new SimpleAuthenticationInfo(employee,employee.getPassword(),this.getName());


            //身份信息，凭证信息（正确），盐，当前realm的名字
            //return new SimpleAuthenticationInfo(employee,employee.getPassword(),ByteSource.Util.bytes(username),this.getName());
        }

        return null;
    }
}
