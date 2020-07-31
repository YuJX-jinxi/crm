package cn.wolfcode.web.controller;


import cn.wolfcode.domain.CustomerTraceHistory;
import cn.wolfcode.service.ICustomerTraceHistoryService;
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
@RequestMapping("/customerTraceHistory")
public class CustomerTraceHistoryController {

    @Autowired
    private ICustomerTraceHistoryService customerTraceHistoryService;


    @RequiresPermissions(value = {"customerTraceHistory:list","客户跟进页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        PageInfo<CustomerTraceHistory> pageInfo = customerTraceHistoryService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        return "customerTraceHistory/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"customerTraceHistory:delete","客户跟进删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            customerTraceHistoryService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"customerTraceHistory:saveOrUpdate","客户跟进新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(CustomerTraceHistory customerTraceHistory){
        if (customerTraceHistory.getId() != null) {
            customerTraceHistoryService.update(customerTraceHistory);
        }else {


            customerTraceHistoryService.save(customerTraceHistory);
        }
        return new JsonResult();
    }
}
