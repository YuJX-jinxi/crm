package cn.wolfcode.service;

import cn.wolfcode.domain.SystemDictionaryItem;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.SystemDictionaryItemQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISystemDictionaryItemService {
    void save(SystemDictionaryItem systemDictionaryItem);
    void delete(Long id);
    void update(SystemDictionaryItem systemDictionaryItem);
    SystemDictionaryItem get(Long id);
    List<SystemDictionaryItem> listAll();
    // 分页查询的方法
    PageInfo<SystemDictionaryItem> query(SystemDictionaryItemQuery qo);

    //根据目录的编码查询明细
    List<SystemDictionaryItem> selectByParentSn(String job);
}
