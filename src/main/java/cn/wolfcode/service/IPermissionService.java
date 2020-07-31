package cn.wolfcode.service;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IPermissionService {
    //增加功能
    void save(Permission permission);
    //删除功能
    void delete(Long id);
    //查询功能
    Permission get(Long id);
    //修改功能
    void update(Permission permission);
    //查询全部功能
    List<Permission> listAll();

    //分页查询功能
    PageInfo<Permission> query(QueryObject qo);


    void reload();


    List<String> selectExpressionByEmpId(Long employeeId);
}
