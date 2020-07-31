package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Department;
import cn.wolfcode.mapper.DepartmentMapper;
import cn.wolfcode.qo.PageResult;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DepartmentServiceImpl implements
        IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public void save(Department department) {
            departmentMapper.insert(department);
    }

    @Override
    public void delete(Long id) {
            departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Department department) {

        departmentMapper.updateByPrimaryKey(department);
    }

    @Override
    public List<Department> listAll() {
        return departmentMapper.selectAll();
    }


    @Override
    public PageInfo<Department> query(QueryObject qo) {
        /*//查询需要显示的页面
        int count = departmentMapper.selectForCount(qo);
        //如果表中结果集数据为0,那么返回一个空的数据给页面去显示
        //Collections.emptyList()空数组
        if (count == 0) {
            return new PageResult<>(qo.getCurrentPage(), qo.getPageSize(),count , Collections.emptyList());
        }
        //如果出传进来的是一个空的数据,前面就会直接返回一个空的数据,就是一个空的界面
        //下面是添加判断,防止用户在删除页面的时候,显示空数据的页面给用户查看
        //由于删除单条数据的时候,显示给用户的是一个超出总页数的,空数据页面给用户查看,那么这里就需要修改了

        int currentPage = qo.getCurrentPage();//获取当前页
        int pageSize = qo.getPageSize();//获取当前页需要显示的数据
        //计算出总页数
        int totalPage=count%pageSize==0?count/pageSize:count/pageSize+1;
        //如果当前页,小于或者都等于1
        if (currentPage<=1){
            //那么就把当前页设置为1
            qo.setCurrentPage(1);
        }
        //如果当前页大于或者等于末页
        if (currentPage>=totalPage){
            qo.setCurrentPage(totalPage);
        }*/


        //告诉插件要开始分页了,传入当前页和每个显示多少条数据
        //调用了 PageHelper.startPage的方法后,会对下一句准备执行的 sql 进行分页
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<Department> departments = departmentMapper.selectForList(qo);
        //return new PageResult<>(qo.getCurrentPage(), qo.getPageSize(),count , departments);

        return new PageInfo<Department>(departments);
    }
}
