package cn.wolfcode.mapper;

import cn.wolfcode.domain.Department;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department department);

Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department department);

    //int selectForCount(QueryObject qo);

    List<Department>selectForList(QueryObject qo);

}