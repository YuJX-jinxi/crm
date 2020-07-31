package cn.wolfcode.web.controller;


import cn.wolfcode.domain.Notice;
import cn.wolfcode.service.INoticeService;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.util.UserContext;
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
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private INoticeService noticeService;


    //@RequiresPermissions(value = {"notice:list","公告页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        PageInfo<Notice> pageInfo = noticeService.query(qo);
        //遍历所有的 pageInfo
        for (Notice notice:pageInfo.getList()){
            Notice no = noticeService.selectSee(notice.getId(), UserContext.getCurrentUser().getId());
            if (no != null){
                notice.setStatus(1);
            }
        }
        model.addAttribute("pageInfo", pageInfo);
        return "notice/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"notice:delete","公告删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            noticeService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"notice:saveOrUpdate","公告新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Notice notice){
        if (notice.getId() != null) {
            noticeService.update(notice);
        }else {
            noticeService.save(notice);
        }
        return new JsonResult();
    }
}
