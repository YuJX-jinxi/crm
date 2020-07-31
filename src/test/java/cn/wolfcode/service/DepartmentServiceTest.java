package cn.wolfcode.service;

import cn.wolfcode.domain.Department;
import cn.wolfcode.domain.Employee;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DepartmentServiceTest {

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IEmployeeService employeeService;
    @Test
    public void save() {
        Department department = new Department();
        department.setName("设计部");
        department.setSn("SJ");
        departmentService.save(department);

    }



     @Test
    public void test(){
         List<Employee> employees = employeeService.listAll();
         for (Employee employee:employees){
             //给新密码加密后再设置
             Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getName(), 2);

             employee.setPassword(md5Hash.toString());
             employeeService.updatePwd(employee);
         }
     }
}