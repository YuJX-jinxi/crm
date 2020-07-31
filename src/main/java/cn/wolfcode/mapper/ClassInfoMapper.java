package cn.wolfcode.mapper;

import cn.wolfcode.domain.ClassInfo;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface ClassInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassInfo record);

    ClassInfo selectByPrimaryKey(Long id);

    List<ClassInfo> selectAll();

    int updateByPrimaryKey(ClassInfo record);

    List<ClassInfo> selectForList(QueryObject qo);
}