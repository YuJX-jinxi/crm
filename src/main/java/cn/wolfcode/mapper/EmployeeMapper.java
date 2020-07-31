package cn.wolfcode.mapper;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    //int selectForCount(QueryObject qo);

    void updatePwd(Employee employee);

    List<Employee> selectForList(QueryObject qo);


    void insertRelation(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

    void deleteRelation( Long id);
    //查询员工信息
    Employee selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    void setStatus(Employee employee);

    void batchDelete( Long[] ids);

    Employee selectByName(String name);

    //根据角色编码查询员工
    List<Employee>selectByRoleSn(String...sns);
}