package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.mapper.CustomerMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ICustomerService;
import cn.wolfcode.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public void save(Customer customer) {

        //新增客户时需要自动设置录入人,销售员为当前登录用户,设置录入事件为当前时间
        customer.setInputUser(UserContext.getCurrentUser());
        customer.setSeller(UserContext.getCurrentUser());
        customer.setInputTime(new Date());
        customerMapper.insert(customer);
    }

    @Override
    public void delete(Long id) {
        customerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public Customer get(Long id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Customer> listAll() {
        return customerMapper.selectAll();
    }

    @Override
    public PageInfo<Customer> query(QueryObject qo) {
        //后面加 排序语句
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize(),"input_time desc"); //对下一句sql进行自动分页
        List<Customer> customers = customerMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<Customer>(customers);
    }

    @Override
    public List<Customer> selectStatusAll(int i) {
        return customerMapper.selectStatusAll(i);
    }
}
