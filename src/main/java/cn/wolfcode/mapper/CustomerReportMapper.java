package cn.wolfcode.mapper;

import cn.wolfcode.qo.CustomerReportQuery;
import java.util.HashMap;
import java.util.List;

public interface CustomerReportMapper {
     //确定查询的结果有多条数据,那么要用 List 封装
    //泛型  :  代表的是一行数据封装的类型
    //方式一 :自定义一个类来封装 查询出来的结果  在类中 添加 groupType  ,   number 两个字段



    //方式二,不需要自定义类,直接用 Map  封装   键值对, key  value  一个map中 可以存储多个键值对   查询几列就有几个键值对  列名作为 key
    List<HashMap>selectForList(CustomerReportQuery qo);

    //读到第一行的数据,每一行new 一个 map
    //HadMap map=new HasMap()
    //map.put ('id',1)    以列名作为 key
    //map.put ('name',"总经办") //以列名作为 key

    //list .ad(map)
}