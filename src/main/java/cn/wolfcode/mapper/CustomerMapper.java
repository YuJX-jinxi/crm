package cn.wolfcode.mapper;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.qo.QueryObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    Customer selectByPrimaryKey(Long id);

    List<Customer> selectAll();

    int updateByPrimaryKey(Customer record);

    List<Customer> selectForList(QueryObject qo);

    void updateSeller(@Param("customerId") Long customerId, @Param("sellerId") Long sellerId);

    List<Customer> selectStatusAll(int i);

    void updateStatus(@Param("status") Integer status, @Param("customerId") Long customerId);

}