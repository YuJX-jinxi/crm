package cn.wolfcode.web.controller;


import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.EmployeeQueryObject;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.service.IDepartmentService;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.service.IRoleService;
import cn.wolfcode.util.RequiredPermission;
import cn.wolfcode.util.UserContext;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    //因为多表查询要查询出部门表的名称
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IRoleService roleService;

    //分页查询当前页需要显示的数据
    @RequestMapping("/list")
    @RequiresPermissions( value = {"employee:list","员工列表"},logical = Logical.OR)
    public String list(Model model, @ModelAttribute("qo") EmployeeQueryObject qo) {
        //把查询出来的数据封装到一个结果集中,去页面上显示
        PageInfo<Employee> pageInfo = employeeService.query(qo);
        //把结果集封装起来,带到页面上去显示
        model.addAttribute("pageInfo", pageInfo);
        //查询所有部门
        model.addAttribute("departments", departmentService.listAll());
        //真正找的视图 :/WEB-INF/views/employee/potentialList.ftl
        return "employee/list";
    }

    //删除功能   http://localhost/employee/delete.do?id=1
    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions( value = {"employee:delete","员工删除"},logical = Logical.OR)
    public JsonResult delete(Long id) {
        if (id!=null){
            employeeService.delete(id);
        }
        //为了方便,不需要每次在这里添加true
            //直接在 JsonResul 中添加默认值 true
            return  new JsonResult();

    }

    //去处理新增的请求 http://localhost/employee/input.do
    @RequestMapping("/input")
    public String input(Model model, Long id) {
        //查询所有的部门信息
        model.addAttribute("departments", departmentService.listAll());

        //如果带有id,那么就通过员工业务层拿到当前id的员工信息,作为回显
        if (id != null) {
            model.addAttribute("employee", employeeService.get(id));
            System.out.println(model.addAttribute("employee", employeeService.get(id)));
        }
        model.addAttribute("roles",roleService.listAll());
        //真正找的视图 :/WEB-INF/views/employee/input.ftl
        return "employee/input";
    }

    //处理新增的请求  POST的请求 : http://localhost/employee/saveOrUpdate.do
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions( value = {"employee:saveOrUpdate","员工新增/编辑"},logical = Logical.OR)
    @ResponseBody
    //因为保存页面传进来了个 id 数组
    public JsonResult saveOrUpdate(Employee employee,Long[]ids) {
        //如果有id,那就是修改
        if (employee.getId() != null) {
            //就要去业务层中解决问题
            employeeService.update(employee,ids);
        } else {
            //否则就是保存操作
            employeeService.save(employee,ids);
        }
        return new JsonResult();
    }

    //处理密码修改的问题
    @RequestMapping("/updatePwd")
    @ResponseBody
    public JsonResult updatePwd(String oldPassword,String newPassword) {
        //原密码不为空
        if (oldPassword!=null){
            //获取到当前用户
            Employee employee = UserContext.getCurrentUser();
            //给用户输入的旧密码加密,再进行匹配
            Md5Hash oldMd5Hash = new Md5Hash(oldPassword, employee.getName(), 2);


            //判断当前用户的真实密码,是否与页面输入进来的原密码一致
            if (employee.getPassword().equals(oldMd5Hash.toString())){
                //如果是一致,那么就设置新密码
                //给新密码加密后再设置
                Md5Hash md5Hash = new Md5Hash(newPassword, employee.getName(), 2);
                employee.setPassword(md5Hash.toString());
                //更新密码
                employeeService.updatePwd(employee);
                return new JsonResult();
            }else{
                return new JsonResult(false,"原密码不正确");
            }
        }
        return new JsonResult(false,"操作失败");
    }

    //去处理新增的请求 http://localhost/employee/input.do
    @RequestMapping("/resetPwd")
    @ResponseBody
    public JsonResult resetPwd(Long id ,String newPassword) {
            if (newPassword!=null && newPassword !=""){
                //获取到当前用户
                Employee employee = employeeService.get(id);
                //给传进来的新密码加密
                Md5Hash md5Hash = new Md5Hash(newPassword, employee.getName(), 2);
                //把新密码重新设置给该对象
                employee.setPassword(md5Hash.toString());
                employeeService.updatePwd(employee);
                return new JsonResult();
            }else {
                return new JsonResult(false,"重置密码不能为空");
            }
    }

    @RequestMapping("/status")
    @ResponseBody
    public JsonResult status(Long id ,boolean status) {
        System.out.println(id);
        System.out.println(status);
        Employee currentUser = UserContext.getCurrentUser();
        if (currentUser.isAdmin()){
            if (id!=null){
                Employee employee = employeeService.get(id);
                employee.setStatus(!employee.isStatus());
                System.out.println(employee.isStatus());
                employeeService.setStatus(employee);
            }
            return new JsonResult();
        }else {
            return new JsonResult(false, "不是管理员你点啥呢");
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/batchDelete")
    @ResponseBody
    public JsonResult batchDelete(/*@RequestParam("ids[]")*/ Long [] ids){
        //在 Service 中添加这个方法
        employeeService.batchDelete(ids);
        return new JsonResult();
    }

    /**
     * 升级版
     * 验证用户名是否存在
     * valid 的 key 是插件规定的,不能改
     * true 验证通过   false 验证不能通过
     * @param name
     * @return
     */
    @RequestMapping("/checkName")
    @ResponseBody  //可以不用 Json .toString 转换,因为贴了 ResponseBody 注解在返回的时候,会帮我们把 map 对象转换成 json 对象
    public HashMap checkName(String name,Long id){

        HashMap<Object, Object> map = new HashMap<>();
        //如果id 值为 null ,那么当前对象也为null
        Employee employee = employeeService.get(id);
        //判断当前 id 是否有值,即是判断当前页是 添加页面还是编辑页面
        if (id==null || !name.equals(employee.getName())){
            //把 name 放前面,是防止空指针
            //在 Service 层 添加对应 查询名字的方法
            Employee employee3 = employeeService.selectByName(name);
            //结果为 true 那么就会显示绿色通过
            //结果为 false 那么就会显示页面上 message 的信息
            map.put("valid", employee3 == null);
            return map;
        }
        //那么就是编辑页面
        map.put("valid", true);
        return map;
    }

    /**
     * 验证用户名是否存在
     * valid 的 key 是插件规定的,不能改
     * true 验证通过   false 验证不能通过
     * @param name
     * @return
     */
    /*@RequestMapping("/checkName")
    @ResponseBody  //可以不用 Json .toString 转换,因为贴了 ResponseBody 注解在返回的时候,会帮我们把 map 对象转换成 json 对象
        public HashMap checkName(String name,Long id){

        HashMap<Object, Object> map = new HashMap<>();
        //判断当前 id 是否有值,即是判断当前页是 添加页面还是编辑页面
        if (id!=null){
            //如果不等于 null ,那么就是编辑页面  查询员工信息
            Employee employee = employeeService.get(id);
            //判断当前员工名字是否与传进来的值一直
            if (employee.getName().equals(name)){
                //如果一致,就放行
                map.put("valid", true);
                return map;
            }else {
                //在 Service 层 添加对应 查询名字的方法
                Employee employee2 = employeeService.selectByName(name);
                //结果为 true 那么就会显示绿色通过
                //结果为 false 那么就会显示页面上 message 的信息
                map.put("valid", employee2 == null);
                return map;
            }
        }else {
            //在 Service 层 添加对应 查询名字的方法
            Employee employee3 = employeeService.selectByName(name);
            //结果为 true 那么就会显示绿色通过
            //结果为 false 那么就会显示页面上 message 的信息
            map.put("valid", employee3 == null);
            return map;
        }
    }*/

    /***
     * 导出 文档
     * @param response
     * @throws IOException
     */
    @RequestMapping("/exportXls")
    //因为需要把数据响应给浏览器,那么就需要  HttpServletResponse 这个参数
    public void exportXls(HttpServletResponse response) throws IOException {
        //设置文件下载的响应头
        response.setHeader("Content-Disposition","attachment;filename=employee.xls");
        //在业务层中 创建 exccel 为了让责任更加分离
        Workbook wb =employeeService.exporXls();
        //把 excel 的数据 输出到浏览器
        wb.write(response.getOutputStream());
    }

    /**
     * 导入 文档
     * MultipartFile  无论传什么文件,都是使用它来封装,不管是图片还是文档,还是视频
     */
    @RequestMapping("/importXls")
    @ResponseBody
    public JsonResult importXls(MultipartFile file) throws IOException {
        //创建 Service 层的  importXls 方法,让责任更加分离
        employeeService.importXls(file);
        return new JsonResult();
    }
}
