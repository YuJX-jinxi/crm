package cn.wolfcode.mapper;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    //int selectForCount(QueryObject qo);

    List<Permission> selectForList(QueryObject qo);

    List<String>selectAllExpression();

    List<String>selectExpressionByEmpId(Long id);



}