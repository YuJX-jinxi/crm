package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.util.RequiredPermission;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;


    //处理查询所有部门的请求,和分页查询的请求
    @RequestMapping("/list")
    @RequiresPermissions(value = {"permission:list","权限列表"},logical = Logical.OR)
    public String list(Model model, QueryObject qo){

        //把返回的结果集对象带到页面去显示数据
        model.addAttribute("pageInfo",permissionService.query(qo));
        //真正找的视图 :/WEB-INF/views/permission/potentialList.ftl
        return "permission/list";
    }

    //删除部门的请求 http://localhost/permission/delete.do?id=1
    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions(value = {"permission:delete","权限删除"},logical = Logical.OR)
    public JsonResult delete(Long id){
           if (id!=null){
               permissionService.delete(id);
           }
           //为了方便,不需要每次在这里添加true
           //直接在 JsonResul 中添加默认值 true
           return  new JsonResult();
    }

    //处理去新增的请求 http://localhost/permission/input.do
    @RequestMapping("/input")
    public String input(Model model,Long id){
        if (id != null) {
            //如果id 不为空,那么就通过id 查询出一个对象 ,并通过模型传到页面去做回显
            model.addAttribute("permission",permissionService.get(id));
        }
        //真正找的视图 :/WEB-INF/views/permission/input.ftl
        return "permission/input";
    }

    //处理新增或者修改的请求 http://localhost/permission/saveOrUpdate.do
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"permission:saveOrUpdate","权限新增/编辑"},logical = Logical.OR)
    public String saveOrUpdate(Permission permission){
        //如果提交请求 di不为空,那么就是修改,如果为空,那就是新增
        if (permission.getId() != null) {
            permissionService.update(permission);
        }else {
            permissionService.save(permission);
        }
        return "redirect:/permission/list.do" ;
    }


    @RequestMapping("/reload")
    @ResponseBody
    public JsonResult reload() {
            permissionService.reload();
            //为了方便,不需要每次在这里添加true
            //直接在 JsonResul 中添加默认值 true
            return new JsonResult();
    }
}
