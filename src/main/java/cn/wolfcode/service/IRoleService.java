package cn.wolfcode.service;

import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IRoleService {
    //增加功能
    void save(Role role, Long[] ids);
    //删除功能
    void delete(Long id);
    //查询功能
    Role get(Long id);
    //修改功能
    void update(Role role, Long[] ids);
    //查询全部功能
    List<Role> listAll();

    //分页查询功能
    PageInfo<Role> query(QueryObject qo);


    List<String> selectSnByEmpId(Long employeeId);
}
