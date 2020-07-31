package cn.wolfcode.web.controller;


import cn.wolfcode.domain.SystemDictionaryItem;
import cn.wolfcode.qo.SystemDictionaryItemQuery;
import cn.wolfcode.service.ISystemDictionaryItemService;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.service.ISystemDictionaryService;
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
@RequestMapping("/systemDictionaryItem")
public class SystemDictionaryItemController {

    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequiresPermissions(value = {"systemDictionaryItem:list","字典明细页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") SystemDictionaryItemQuery qo){
        //给 qo 对象重新封装了一下,给他添加了一个查询条件,用于明细查询
        PageInfo<SystemDictionaryItem> pageInfo = systemDictionaryItemService.query(qo);
        model.addAttribute("pageInfo", pageInfo);

        //查询所有的字典目录
        model.addAttribute("dics", systemDictionaryService.listAll());

        return "systemDictionaryItem/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"systemDictionaryItem:delete","字典明细删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            systemDictionaryItemService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"systemDictionaryItem:saveOrUpdate","字典明细新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(SystemDictionaryItem systemDictionaryItem){
        if (systemDictionaryItem.getId() != null) {
            systemDictionaryItemService.update(systemDictionaryItem);
        }else {
            systemDictionaryItemService.save(systemDictionaryItem);
        }
        return new JsonResult();
    }
}
