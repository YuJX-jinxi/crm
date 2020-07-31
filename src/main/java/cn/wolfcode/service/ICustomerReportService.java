package cn.wolfcode.service;

import cn.wolfcode.qo.CustomerReportQuery;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

public interface ICustomerReportService {
    PageInfo selectForList(CustomerReportQuery qo);

    //查询全部
    List<HashMap>selectAll(CustomerReportQuery qo);
}
