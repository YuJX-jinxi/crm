package cn.wolfcode.mapper;

import cn.wolfcode.domain.CustomerTraceHistory;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface CustomerTraceHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerTraceHistory record);

    CustomerTraceHistory selectByPrimaryKey(Long id);

    List<CustomerTraceHistory> selectAll();

    int updateByPrimaryKey(CustomerTraceHistory record);

    List<CustomerTraceHistory> selectForList(QueryObject qo);
}