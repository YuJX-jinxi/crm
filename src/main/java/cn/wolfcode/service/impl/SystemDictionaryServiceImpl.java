package cn.wolfcode.service.impl;

import cn.wolfcode.domain.SystemDictionary;
import cn.wolfcode.mapper.SystemDictionaryMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ISystemDictionaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {

    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;


    @Override
    public void save(SystemDictionary systemDictionary) {
        systemDictionaryMapper.insert(systemDictionary);
    }

    @Override
    public void delete(Long id) {
        systemDictionaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(SystemDictionary systemDictionary) {
        systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
    }

    @Override
    public SystemDictionary get(Long id) {
        return systemDictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionary> listAll() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public PageInfo<SystemDictionary> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<SystemDictionary> systemDictionarys = systemDictionaryMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<SystemDictionary>(systemDictionarys);
    }
}
