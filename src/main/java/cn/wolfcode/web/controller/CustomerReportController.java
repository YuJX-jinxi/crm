package cn.wolfcode.web.controller;


import cn.wolfcode.domain.ClassInfo;
import cn.wolfcode.qo.CustomerReportQuery;
import cn.wolfcode.qo.EmployeeQueryObject;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.service.IClassInfoService;
import cn.wolfcode.service.ICustomerReportService;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.util.MessageUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/customerReport")
public class CustomerReportController {
    @Autowired
    private ICustomerReportService customerReportService;

    /**
     * 表格形式的报表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") CustomerReportQuery qo){

        model.addAttribute("pageInfo",customerReportService.selectForList(qo));

        return "customerReport/list";
    }

    /**
     * 柱状图(不用分页)
     */
    @RequestMapping("/listBar")
    public String listBar(Model model,@ModelAttribute("qo") CustomerReportQuery qo){
        //查询数据库分组的数据
        List<HashMap> list = customerReportService.selectAll(qo);
        ///需要的数据   x轴的数据 : ['孙总','钱二明',"赵一明","王五"] 员工姓名
                // y轴的数据 [25,67,38,18],//查询的结果

        List x=new ArrayList();
        List y=new ArrayList();
        //遍历 sql 查出来的 集合 List ,获取到每一个 map
        for (HashMap map:list){
            x.add(map.get("groupType"));
            y.add(map.get("number"));
        }
        //共享到页面
        model.addAttribute("x", JSON.toJSONString(x));
        model.addAttribute("y",JSON.toJSONString(y));
        model.addAttribute("groupTypeName",MessageUtil.changMsg(qo));

        return "customerReport/listBar";
    }

    /**
     * 饼状图(不用分页)
     */
    @RequestMapping("/listPie")
    public String listPie(Model model,@ModelAttribute("qo") CustomerReportQuery qo){
        //查询数据库分组的数据
        List<HashMap> list = customerReportService.selectAll(qo);
        ///需要的数据   x轴的数据 : ['孙总','钱二明',"赵一明","王五"] 员工姓名
        // y轴的数据 [25,67,38,18],//查询的结果

        List legend=new ArrayList();
        List series=new ArrayList();
        //遍历 sql 查出来的 集合 List ,获取到每一个 map
        for (HashMap map:list){
            //获取员工名称
            legend.add(map.get("groupType"));
            HashMap temp=new HashMap();
            temp.put("name",map.get("groupType"));
            temp.put("value",map.get("number"));
            //获取员工 新增潜在看客户人数
            series.add(temp);
        }
        //共享到页面
        model.addAttribute("legend", JSON.toJSONString(legend));
        model.addAttribute("series",JSON.toJSONString(series));
        model.addAttribute("groupTypeName",MessageUtil.changMsg(qo));


        return "customerReport/listPie";
    }
}
