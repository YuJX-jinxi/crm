package cn.wolfcode.web.controller;


import cn.wolfcode.domain.ClassInfo;
import cn.wolfcode.qo.EmployeeQueryObject;
import cn.wolfcode.service.IClassInfoService;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.service.IEmployeeService;
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
@RequestMapping("/classInfo")
public class ClassInfoController {

    @Autowired
    private IClassInfoService classInfoService;
    @Autowired
    private IEmployeeService employeeService;

    @RequiresPermissions(value = {"classInfo:list","班级管理页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") EmployeeQueryObject qo){
        PageInfo<ClassInfo> pageInfo = classInfoService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("employees",employeeService.listAll());
        return "classInfo/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"classInfo:delete","班级管理删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            classInfoService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"classInfo:saveOrUpdate","班级管理新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(ClassInfo classInfo){
        if (classInfo.getId() != null) {
            classInfoService.update(classInfo);
        }else {
            classInfoService.save(classInfo);
        }
        return new JsonResult();
    }
}
