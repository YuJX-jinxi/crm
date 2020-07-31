package cn.wolfcode.web.controller;



import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.service.IRoleService;
import cn.wolfcode.util.RequiredPermission;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPermissionService permissionService;
    @RequestMapping("list")
    @RequiresPermissions(value = {"role:list","角色列表"},logical = Logical.OR)
    public String list(Model model, @ModelAttribute("qo")QueryObject queryObject){
        model.addAttribute("pageInfo",roleService.query(queryObject) );
        model.addAttribute("departments",departmentService.listAll());
        return "role/list";
    }


    @RequestMapping("input")
    public String input(Model model,Long id){
        //查询所有权限的信息
        model.addAttribute("permissions",permissionService.listAll());

        if (id!=null){
            model.addAttribute("role",roleService.get(id));
        }

        return "role/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiresPermissions(value = {"role:saveOrUpdate","角色新增/编辑"},logical = Logical.OR)
    public String saveOrUpdate(Role role,Long [] ids){
        if (role.getId()!=null){
           roleService.update(role,ids);
        }else {
            roleService.save(role,ids);
        }
        return "redirect:/role/list.do";
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequiresPermissions(value = {"role:delete","角色删除"},logical = Logical.OR)
    public JsonResult delete(Long id){
            roleService.delete(id);
            //为了方便,不需要每次在这里添加true
            //直接在 JsonResul 中添加默认值 true
            return  new JsonResult();
    }
}
