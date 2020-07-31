package cn.wolfcode.service;

import cn.wolfcode.domain.Department;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IDepartmentService {
    //增加功能
    void save(Department department);
    //删除功能
    void delete(Long id);
    //查询功能
    Department get(Long id);
    //修改功能
    void update(Department department);
    //查询全部功能
    List<Department> listAll();

    //分页查询功能
    PageInfo<Department> query(QueryObject qo);

}
