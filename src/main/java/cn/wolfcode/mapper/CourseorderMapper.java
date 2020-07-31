package cn.wolfcode.mapper;

import cn.wolfcode.domain.Courseorder;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface CourseorderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Courseorder record);

    Courseorder selectByPrimaryKey(Long id);

    List<Courseorder> selectAll();

    int updateByPrimaryKey(Courseorder record);

    List<Courseorder> selectForList(QueryObject qo);

}