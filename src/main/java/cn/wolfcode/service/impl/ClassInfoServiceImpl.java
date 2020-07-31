package cn.wolfcode.service.impl;

import cn.wolfcode.domain.ClassInfo;
import cn.wolfcode.mapper.ClassInfoMapper;
import cn.wolfcode.qo.EmployeeQueryObject;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IClassInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassInfoServiceImpl implements IClassInfoService {

    @Autowired
    private ClassInfoMapper classInfoMapper;


    @Override
    public void save(ClassInfo classInfo) {
        classInfoMapper.insert(classInfo);
    }

    @Override
    public void delete(Long id) {
        classInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ClassInfo classInfo) {
        classInfoMapper.updateByPrimaryKey(classInfo);
    }

    @Override
    public ClassInfo get(Long id) {
        return classInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ClassInfo> listAll() {
        return classInfoMapper.selectAll();
    }

    @Override
    public PageInfo<ClassInfo> query(EmployeeQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<ClassInfo> classInfos = classInfoMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<ClassInfo>(classInfos);
    }
}
