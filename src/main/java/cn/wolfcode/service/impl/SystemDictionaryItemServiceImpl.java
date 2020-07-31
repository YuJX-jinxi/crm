package cn.wolfcode.service.impl;

import cn.wolfcode.domain.SystemDictionaryItem;
import cn.wolfcode.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.SystemDictionaryItemQuery;
import cn.wolfcode.service.ISystemDictionaryItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {

    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;


    @Override
    public void save(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.insert(systemDictionaryItem);
    }

    @Override
    public void delete(Long id) {
        systemDictionaryItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
    }

    @Override
    public SystemDictionaryItem get(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionaryItem> listAll() {
        return systemDictionaryItemMapper.selectAll();
    }

    @Override
    public PageInfo<SystemDictionaryItem> query(SystemDictionaryItemQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<SystemDictionaryItem> systemDictionaryItems = systemDictionaryItemMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<SystemDictionaryItem>(systemDictionaryItems);
    }

    @Override
    public List<SystemDictionaryItem> selectByParentSn(String job) {
        return systemDictionaryItemMapper.selectByParentSn(job);
    }
}
