package cn.wolfcode.web.controller;


import cn.wolfcode.domain.CustomerTransfer;
import cn.wolfcode.service.ICustomerTransferService;
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
@RequestMapping("/customerTransfer")
public class CustomerTransferController {

    @Autowired
    private ICustomerTransferService customerTransferService;


    @RequiresPermissions(value = {"customerTransfer:list","客户移交页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        PageInfo<CustomerTransfer> pageInfo = customerTransferService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        return "customerTransfer/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"customerTransfer:delete","客户移交删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            customerTransferService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"customerTransfer:saveOrUpdate","客户移交新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(CustomerTransfer customerTransfer){
        if (customerTransfer.getId() != null) {
            customerTransferService.update(customerTransfer);
        }else {
            customerTransferService.save(customerTransfer);
        }
        return new JsonResult();
    }
}
