package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.exception.LogicException;
import cn.wolfcode.mapper.EmployeeMapper;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements
        IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void save(Employee employee,Long[] ids) {

        //对密码进行加密
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getName(), 2);
        employee.setPassword(md5Hash.toString());

        employeeMapper.insert(employee);
            //关联关系
        //代表角色是有被选中的
            if (ids!=null && ids.length>0){
                //遍历当前id 数组
                for (Long roleId:ids){
                    //在中间表中存入数据   insertRelation :插入关系
                    employeeMapper.insertRelation(employee.getId(),roleId);
                }
            }
    }

    @Override
    public void delete(Long id) {
            employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Employee employee,Long[] ids) {

        employeeMapper.updateByPrimaryKey(employee);
        //删除关系
        employeeMapper.deleteRelation(employee.getId());
        //关联关系
        if (ids!=null && ids.length>0){
            for (Long roleId:ids){
                employeeMapper.insertRelation(employee.getId(),roleId);
            }
        }
    }

    @Override
    public List<Employee> listAll()  {
        return employeeMapper.selectAll();
    }


    @Override
    public PageInfo<Employee> query(QueryObject qo) {
       /* //查询需要显示的页面
        int count = employeeMapper.selectForCount(qo);
        //如果表中结果集数据为0,那么返回一个空的数据给页面去显示
        //Collections.emptyList()空数组
        if (count == 0) {
            return new PageResult<>(qo.getCurrentPage(), qo.getPageSize(),count , Collections.emptyList());
        }
        //如果出传进来的是一个空的数据,前面就会直接返回一个空的数据,就是一个空的界面
        //下面是添加判断,防止用户在删除页面的时候,显示空数据的页面给用户查看
        //由于删除单条数据的时候,显示给用户的是一个超出总页数的,空数据页面给用户查看,那么这里就需要修改了

        int currentPage = qo.getCurrentPage();//获取当前页
        int pageSize = qo.getPageSize();//获取当前页需要显示的数据
        //计算出总页数
        int totalPage=count%pageSize==0?count/pageSize:count/pageSize+1;
        //如果当前页,小于或者都等于1
        if (currentPage<=1){
            //那么就把当前页设置为1
            qo.setCurrentPage(1);
        }
        //如果当前页大于或者等于末页
        if (currentPage>=totalPage){
            qo.setCurrentPage(totalPage);
        }*/

        //有数据,就根据数据提供的条件去查询
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<Employee> employees = employeeMapper.selectForList(qo);
        return new PageInfo<Employee>(employees);
        //return new PageResult<>(qo.getCurrentPage(), qo.getPageSize(),count , employees);
    }

    //登陆页面
    @Override
    public Employee login(String username, String password) {
        //使用工具
        //判断 传进来的数据 不为空 并且 非空字符串
        if (!(StringUtils.hasText(username))){
            throw new LogicException("用户名不能为空!");
        }
        if (!(StringUtils.hasText(password))){
            throw new LogicException("密码不能为空!");
        }
       // 通过用户名和密码,查询员工信息,并返回一个员工对象
        Employee employee = employeeMapper.selectByUsernameAndPassword(username, password);
        /*System.out.println(employee);*/
        //判断当前员工信息,如果查不到为null
        if (employee==null){
            //通知调用者出现异常
            throw new LogicException("账号和密码不匹配");
        }
        return employee;
    }

    @Override
    public void updatePwd(Employee employee) {
        employeeMapper.updatePwd(employee);
    }


    @Override
    public void setStatus(Employee employee) {

        employeeMapper.setStatus(employee);
    }

    //批量删除
    @Override
    public void batchDelete(Long[] ids) {
        //判断当前数组是否有参数
        if (ids!=null&&ids.length>0){
            //在 Mapper 中 添加 batchDelete 方法.并且编写 SQL 语句
            employeeMapper.batchDelete(ids);
        }
    }

    @Override
    public Employee selectByName(String name) {
        return employeeMapper.selectByName(name);
    }

    @Override
    public Workbook exporXls() {
        //查询所有员工
        List<Employee> employees = employeeMapper.selectAll();

        //使用 poi 创建一个Workbook excel:电子表格
        Workbook wb = new HSSFWorkbook();
        //创建一个 excel 中的表, 可以创建多个,因为可以有多个页面:就是可以有多个 Sheet 对象
        //createSheet  : 设置当前页的 名称
        Sheet sheet = wb.createSheet("员工通讯录");

        //创建标题行数据,从 0 开始
        Row row = sheet.createRow(0);
        //编写标题栏
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("邮箱");
        row.createCell(2).setCellValue("年龄");
        //遍历员工数组信息
        for (int i=0;i<employees.size();i++) {
            //通过当前数组的 元素 查询员工信息
            Employee employee = employees.get(i);
            //创建一行数据 ,从哪一行开始编写数据  这是从 0 开始的
            row=sheet.createRow(i+1);//加1,这个1是减少标题行的数据
            //创建单元格,写入内容到单元格
            //能存入数据库的数据,都是没有问题的
            row.createCell(0).setCellValue(employee.getName());
            row.createCell(1).setCellValue(employee.getEmail());
            row.createCell(2).setCellValue(employee.getAge());
        }
        return wb;
    }

    @Override
    public void importXls(MultipartFile file)  throws IOException {
        //读取上传的文件,并封装成为一个 Workbook  对象
        Workbook wb = new HSSFWorkbook(file.getInputStream());
        //拿到 索引位置在 0 的 sheet 表
        Sheet sheet = wb.getSheetAt(0);
        ///获取最后一行的索引
        int lastRowNum = sheet.getLastRowNum();
        //System.out.println(lastRowNum);//知道当前 是用索引来做参数,还是直接参数
        //为什么 i=1 , 因为索引行不需要读取
        //要完全读完 所在行的信息,所以要用等于  lastRowNum是代表索引所以需要 使用 =
        for (int i=1;i<=lastRowNum;i++){
            //获取每一行数据
            Row row = sheet.getRow(i);
            Employee employee = new Employee();
            /*if (row.getCell(0)==null||row.getCell(1)==null){  //判断空值问题,用户名或者邮箱其中一个为空就不再读取
                break;
            }*/
            //getStringCellValue  要 get 对应的类型
            employee.setName(row.getCell(0).getStringCellValue());
            employee.setEmail(row.getCell(1).getStringCellValue());

            //判断用户传进来的年龄单元格格式    判断是否是数字
            Cell cell = row.getCell(2);
            if (cell.getCellType()==CellType.NUMERIC){//如果它的类型是数字,就当做数字写入   数字类型的单元格
                int age = (int) cell.getNumericCellValue();
                /*if (18 >age || age>60){
                    throw  new LogicException("第"+i+"行数据有问题,年龄的范围在 18 - 60 之间");
                }*/
                employee.setAge((int) cell.getNumericCellValue());
            }else {//如果是字符串,那么就强转它     文本类型的单元格
                employee.setAge(Integer.valueOf(cell.getStringCellValue()));
            }

            //把字符串信息,强转成数字类型
            employeeMapper.insert(employee);
            //或者 可以使用   save(employee,null );
        }
    }

    @Override
    public List<Employee> selectByRoleSn(String... sns) {
        return  employeeMapper.selectByRoleSn(sns);
    }
}
