package ${packageName};

import cn.wolfcode.domain.${domainName};
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(${domainName} department);

${domainName} selectByPrimaryKey(Long id);

    List<${domainName}> selectAll();

    int updateByPrimaryKey(${domainName} department);

    //int selectForCount(QueryObject qo);

    List<${domainName}>selectForList(QueryObject qo);

}