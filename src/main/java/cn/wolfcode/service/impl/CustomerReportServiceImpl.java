package cn.wolfcode.service.impl;

import cn.wolfcode.mapper.CustomerMapper;
import cn.wolfcode.mapper.CustomerReportMapper;
import cn.wolfcode.qo.CustomerReportQuery;
import cn.wolfcode.service.ICustomerReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CustomerReportServiceImpl implements ICustomerReportService {

    @Autowired
    private CustomerReportMapper customerReportMapper;

    @Override
    public PageInfo selectForList(CustomerReportQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<HashMap> list = customerReportMapper.selectForList(qo);
        return new PageInfo(list);
    }

    @Override
    public List<HashMap> selectAll(CustomerReportQuery qo) {
        return customerReportMapper.selectForList(qo);
    }
}
