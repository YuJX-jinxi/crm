package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Courseorder;
import cn.wolfcode.mapper.CourseorderMapper;
import cn.wolfcode.mapper.CustomerMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ICourseorderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseorderServiceImpl implements ICourseorderService {

    @Autowired
    private CourseorderMapper courseorderMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void save(Courseorder courseorder) {
        //在保存当前用户前,将当前用户的 状态,从潜在用户修改成正式客户
        customerMapper.updateStatus(1,courseorder.getCustomer().getId() );

        courseorder.setInputTime(new Date());

        courseorderMapper.insert(courseorder);
    }

    @Override
    public void delete(Long id) {
        courseorderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Courseorder courseorder) {
        courseorderMapper.updateByPrimaryKey(courseorder);
    }

    @Override
    public Courseorder get(Long id) {
        return courseorderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Courseorder> listAll() {
        return courseorderMapper.selectAll();
    }

    @Override
    public PageInfo<Courseorder> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<Courseorder> courseorders = courseorderMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<Courseorder>(courseorders);
    }
}
