package cn.wolfcode.service;

import cn.wolfcode.domain.Courseorder;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICourseorderService {
    void save(Courseorder courseorder);
    void delete(Long id);
    void update(Courseorder courseorder);
    Courseorder get(Long id);
    List<Courseorder> listAll();
    // 分页查询的方法
    PageInfo<Courseorder> query(QueryObject qo);
}
