package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.domain.CustomerTransfer;
import cn.wolfcode.mapper.CustomerMapper;
import cn.wolfcode.mapper.CustomerTransferMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ICustomerTransferService;
import cn.wolfcode.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerTransferServiceImpl implements ICustomerTransferService {

    @Autowired
    private CustomerTransferMapper customerTransferMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void save(CustomerTransfer customerTransfer) {
        //把客户的销售人员字段改为新的 销售人员
        customerMapper.updateSeller(customerTransfer.getCustomer().getId(),customerTransfer.getNewSeller().getId() );


        //设置操作人和操作时间
        customerTransfer.setOperator(UserContext.getCurrentUser());
        customerTransfer.setOperateTime(new Date());
        //保存移交历史
        customerTransferMapper.insert(customerTransfer);
    }

    @Override
    public void delete(Long id) {
        customerTransferMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(CustomerTransfer customerTransfer) {
        customerTransferMapper.updateByPrimaryKey(customerTransfer);
    }

    @Override
    public CustomerTransfer get(Long id) {
        return customerTransferMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CustomerTransfer> listAll() {
        return customerTransferMapper.selectAll();
    }

    @Override
    public PageInfo<CustomerTransfer> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize(),"operate_time desc"); //对下一句sql进行自动分页
        List<CustomerTransfer> customerTransfers = customerTransferMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<CustomerTransfer>(customerTransfers);
    }
}
