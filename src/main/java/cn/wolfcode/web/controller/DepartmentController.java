package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Department;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import cn.wolfcode.util.RequiredPermission;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;
    //处理查询所有部门的请求,和分页查询的请求
    //允许配置多个权限表达式,默认判断的逻辑 && 两个都必须要有
    //为了方便获取权限的名字,可以吧逻辑改为 or (其中一个有就可以解决这个问题)
    //@RequiresRoles("admin")//只有管理员角色才可以访问
    @RequiresPermissions(value = {"department:list","部门页面"},logical = Logical.OR)//返回的是一个数组,因为可以存在多个
    @RequestMapping("/list")
   // @RequiredPermission( name = "部门列表",expression = "department:list")
    public String list(Model model, QueryObject qo){
        Subject subject = SecurityUtils.getSubject();
        //需要判断角色或者权限的时候 , shiro 会自动调用 realm 的 doGetAuthenticationInfo 方法,获取用户的数据 进行对比
        System.out.println("admin:" + subject.hasRole("admin"));
        System.out.println("department:list:" + subject.isPermitted("department:list"));


        PageInfo<Department> pageInfo = departmentService.query(qo);
        //把返回的结果集对象带到页面去显示数据
        model.addAttribute("pageInfo",pageInfo);
        //真正找的视图 :/WEB-INF/views/department/potentialList.ftl
        return "department/list";
    }

    //删除部门的请求 http://localhost/department/delete.do?id=1
    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions(value = {"department:delete","部门删除"},logical = Logical.OR)
    public JsonResult delete(Long id){
           if (id!=null){
               departmentService.delete(id);
           }
           //为了方便,不需要每次在这里添加true
           //直接在 JsonResul 中添加默认值 true
           return  new JsonResult();

    }

    //处理去新增的请求 http://localhost/department/input.do
    /*@RequestMapping("/input")
    public String input(Model model,Long id){
        if (id != null) {
            //如果id 不为空,那么就通过id 查询出一个对象 ,并通过模型传到页面去做回显
            model.addAttribute("department",departmentService.get(id));
        }
        //真正找的视图 :/WEB-INF/views/department/input.ftl
        return "department/input";
    }
*/
    //处理新增或者修改的请求 http://localhost/department/saveOrUpdate.do
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"department:saveOrUpdate","部门新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Department department){
        //如果提交请求 di不为空,那么就是修改,如果为空,那就是新增
            if (department.getId() != null) {
                departmentService.update(department);
            }else {
                departmentService.save(department);
            }
            return new JsonResult();
    }
}
