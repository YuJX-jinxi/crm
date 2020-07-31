package cn.wolfcode.web.controller;


import cn.wolfcode.domain.${capitalize};
import cn.wolfcode.service.I${capitalize}Service;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.JsonResult;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/${uncapitalize}")
public class ${capitalize}Controller {

    @Autowired
    private I${capitalize}Service ${uncapitalize}Service;


    @RequiresPermissions(value = {"${uncapitalize}:list","${chinese}页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        PageInfo<${capitalize}> pageInfo = ${uncapitalize}Service.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        return "${uncapitalize}/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"${uncapitalize}:delete","${chinese}删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            ${uncapitalize}Service.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"${uncapitalize}:saveOrUpdate","${chinese}新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(${capitalize} ${uncapitalize}){
        if (${uncapitalize}.getId() != null) {
            ${uncapitalize}Service.update(${uncapitalize});
        }else {
            ${uncapitalize}Service.save(${uncapitalize});
        }
        return new JsonResult();
    }
}
