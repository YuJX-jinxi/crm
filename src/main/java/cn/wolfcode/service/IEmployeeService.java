package cn.wolfcode.service;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {
    //增加功能
    void save(Employee employee,Long[] ids);
    //删除功能
    void delete(Long id);
    //查询功能
    Employee get(Long id);
    //修改功能
    void update(Employee employee,Long[] ids);
    //查询全部功能
    List<Employee> listAll();

    //分页查询功能
    PageInfo<Employee> query(QueryObject qo);

    Employee login(String username, String password);

    void updatePwd(Employee employee);

    void setStatus(Employee employee);

    //批量删除
    void batchDelete(Long[] ids);

    //根据用户名查询员
    Employee selectByName(String name);


    Workbook exporXls();

    void importXls(MultipartFile file) throws IOException;

    List<Employee> selectByRoleSn(String...sns);
}
