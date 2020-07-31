package cn.wolfcode.service;

import cn.wolfcode.domain.CustomerTraceHistory;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerTraceHistoryService {
    void save(CustomerTraceHistory customerTraceHistory);
    void delete(Long id);
    void update(CustomerTraceHistory customerTraceHistory);
    CustomerTraceHistory get(Long id);
    List<CustomerTraceHistory> listAll();
    // 分页查询的方法
    PageInfo<CustomerTraceHistory> query(QueryObject qo);
}
