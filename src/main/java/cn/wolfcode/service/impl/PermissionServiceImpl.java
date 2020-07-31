package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.mapper.PermissionMapper;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.util.RequiredPermission;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements
        IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public void save(Permission permission) {
            permissionMapper.insert(permission);
    }

    @Override
    public void delete(Long id) {
            permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Permission permission) {

        permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public List<Permission> listAll() {
        return permissionMapper.selectAll();
    }


    @Override
    public PageInfo<Permission> query(QueryObject qo) {
       /* //查询需要显示的页面
        int count = permissionMapper.selectForCount(qo);
        //如果表中结果集数据为0,那么返回一个空的数据给页面去显示
        //Collections.emptyList()空数组
        if (count == 0) {
            return new PageResult<>(qo.getCurrentPage(), qo.getPageSize(),count , Collections.emptyList());
        }
        //如果出传进来的是一个空的数据,前面就会直接返回一个空的数据,就是一个空的界面
        //下面是添加判断,防止用户在删除页面的时候,显示空数据的页面给用户查看
        //由于删除单条数据的时候,显示给用户的是一个超出总页数的,空数据页面给用户查看,那么这里就需要修改了

        int currentPage = qo.getCurrentPage();//获取当前页
        int pageSize = qo.getPageSize();//获取当前页需要显示的数据
        //计算出总页数
        int totalPage=count%pageSize==0?count/pageSize:count/pageSize+1;
        //如果当前页,小于或者都等于1
        if (currentPage<=1){
            //那么就把当前页设置为1
            qo.setCurrentPage(1);
        }
        //如果当前页大于或者等于末页
        if (currentPage>=totalPage){
            qo.setCurrentPage(totalPage);
        }
*/
        //有数据,就根据数据提供的条件去查询
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<Permission> permissions = permissionMapper.selectForList(qo);
        //return new PageResult<>(qo.getCurrentPage(), qo.getPageSize(),count , permissions);
        return new PageInfo<Permission>(permissions);
    }

    @Autowired
    private ApplicationContext ctx;//spring的上下文对象,可以拿到里面全部对象

    @Override
    public void reload() {
        //需求:把每个控制器中贴了权限注解的方法装换为权限数据,并插入到数据库
        //查询数据库中已经有的权限表达式

        //获取权限表单中的数据
        List<String> permissions = permissionMapper.selectAllExpression();


        //为什么要拿控制器对象 因为要拿到全部贴了标签的方法,所以要拿控制器对象来筛选
        // 1.获取所有的控制器
        Map<String, Object> map = ctx.getBeansWithAnnotation(Controller.class);//拿到所有贴了Controller的类
        //拿到所有的 控制器 对象
        Collection<Object> controllers = map.values();

        //2.遍历获取:拿到每个控制器里面的方法
        for (Object controller : controllers){

            //如果这个 controller 是普通的 controller 类,那么他的父类就是 object ,object 的方法没有贴注解,效果不影响,就是效率不高
            //判断是否 cglib 代理对象    如果不是,就跳过  如果是就执行下面的代码
            if (!AopUtils.isCglibProxy(controller)){
                continue;
            }

            //通过反射拿到当前 控制器的字节码对象,
            //getSuperclass  获取代理对象的父类,才是我们本来类,这样才能拿到本身的注解(解决重新加载)
            Class<?> clazz = controller.getClass().getSuperclass();//拿到控制器字节码对象  ( controller 是代理类)

        //3.拿到当前循环类的所有方法
            Method[] methods = clazz.getDeclaredMethods();
            //遍历当前类的所有方法
            for (Method method:methods){
            //4.判断该方法有没有贴注解 (shiro 提供的 )

                //拿到指定注解的对象,如果当前方法没有贴 RequiredPermission 这个注解,那么结果为null
                //代理对象中的方法上的注解不能被继承,所以获取不到注解
                RequiresPermissions annotations = method.getAnnotation(RequiresPermissions.class);
                //判断当前标签对象 是否为 null
                if (annotations!=null){
                    //5.从注解中获取我们的权限相关的数据,并封装为权限对象
                    String name = annotations.value()[1];//看当时的权限是怎么贴的注解
                    String expression = annotations.value()[0];//拿0位索引

                    //如果没有存在数据库,就插入进入
                    //contains 包含的意思
                    //判断 expression权限是否存在当前权限表单中
                    if (!permissions.contains(expression)){
                    //6.封装之后就插入到数据库中
                    Permission permission = new Permission();
                    permission.setName(name);
                    permission.setExpression(expression);
                    //保存对象
                    permissionMapper.insert(permission);
                    }

                }
            }

        }
    }

    @Override
    public List<String> selectExpressionByEmpId(Long employeeId) {
        return permissionMapper.selectExpressionByEmpId(employeeId);
    }


}
