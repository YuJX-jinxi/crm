package cn.wolfcode.web.controller;


import cn.wolfcode.domain.SystemDictionary;
import cn.wolfcode.service.ISystemDictionaryService;
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
@RequestMapping("/systemDictionary")
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;


    @RequiresPermissions(value = {"systemDictionary:list","字典目录页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        PageInfo<SystemDictionary> pageInfo = systemDictionaryService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        return "systemDictionary/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"systemDictionary:delete","字典目录删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            systemDictionaryService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"systemDictionary:saveOrUpdate","字典目录新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(SystemDictionary systemDictionary){
        if (systemDictionary.getId() != null) {
            systemDictionaryService.update(systemDictionary);
        }else {
            systemDictionaryService.save(systemDictionary);
        }
        return new JsonResult();
    }
}
