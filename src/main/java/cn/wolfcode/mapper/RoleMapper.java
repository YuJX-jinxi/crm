package cn.wolfcode.mapper;

import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    //int selectForCount(QueryObject qo);

    List<Role> selectForList(QueryObject qo);

    //关联关系
    void insertRelation(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void deleteRelation( Long roleId);

    List<String> selectSnByEmpId(Long employeeId);
}