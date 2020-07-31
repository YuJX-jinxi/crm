package cn.wolfcode.web.controller;


import cn.wolfcode.domain.Customer;
import cn.wolfcode.domain.SystemDictionaryItem;
import cn.wolfcode.qo.CustomerQuery;
import cn.wolfcode.service.ICustomerService;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.service.ISystemDictionaryItemService;
import cn.wolfcode.util.UserContext;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    @Autowired
    private IEmployeeService employeeService;

    @RequiresPermissions(value = {"customer:potentialList","客户页面"},logical = Logical.OR)
    @RequestMapping("/potentialList")
    public String list(Model model, @ModelAttribute("qo") CustomerQuery qo){
        ///判断当前 subject 用户 ,是否包含 admin 或者 Market_Manger 如果有,可以查所有的数据
        Subject subject = SecurityUtils.getSubject();
        //判断当前用户是否超级管理员或者经理
        if (!(subject.hasRole("admin"))||(subject.hasRole("Market_Manger"))){
            //如果没有,就只能查看自己的数据
            qo.setSellerId(UserContext.getCurrentUser().getId());
        }
        //只查询潜在客户状态的数据
        qo.setStatus(Customer.STATUS_COMMON);

        PageInfo<Customer> pageInfo = customerService.query(qo);
        model.addAttribute("pageInfo", pageInfo);

        //查询职业下拉框的数据
        model.addAttribute("jobs",systemDictionaryItemService.selectByParentSn("job"));
        //查询来源下拉框的数据
        model.addAttribute("sources",systemDictionaryItemService.selectByParentSn("source"));

        //查询 角色是  Market  Market_Manager  的员工信息
        model.addAttribute("sellers",employeeService.selectByRoleSn("Market","Market_Manger"));

        //查询来源下拉框的数据   通过字典目录指定的工作名 ,来查询明细中的 该字典id 的明细信息
        List<SystemDictionaryItem> communicationMethod = systemDictionaryItemService.selectByParentSn("communicationMethod");
        model.addAttribute("ccts",communicationMethod);

        return "customer/potentialList";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"customer:delete","客户删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            customerService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"customer:saveOrUpdate","客户新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Customer customer){
        if (customer.getId() != null) {
            customerService.update(customer);
        }else {
            customerService.save(customer);
        }
        return new JsonResult();
    }
}
