package cn.wolfcode.web.controller;


import cn.wolfcode.domain.Courseorder;
import cn.wolfcode.domain.Customer;
import cn.wolfcode.service.IClassInfoService;
import cn.wolfcode.service.ICourseorderService;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.service.ICustomerService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/courseorder")
public class CourseorderController {

    @Autowired
    private ICourseorderService courseorderService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IClassInfoService classInfoService;
    @RequiresPermissions(value = {"courseorder:list","课程订单页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        PageInfo<Courseorder> pageInfo = courseorderService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        //查询潜在客户
        Integer i=0;
        List<Customer>customers=customerService.selectStatusAll(i);
        model.addAttribute("customers",customers);
        model.addAttribute("classInfos",classInfoService.listAll());
        return "courseorder/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"courseorder:delete","课程订单删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            courseorderService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"courseorder:saveOrUpdate","课程订单新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Courseorder courseorder){
        if (courseorder.getId() != null) {
            courseorderService.update(courseorder);
        }else {
            courseorderService.save(courseorder);
        }
        return new JsonResult();
    }
}
