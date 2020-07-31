package cn.wolfcode.domain;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 *
 * 班级管理
 */
@Getter
@Setter
public class ClassInfo {
    private Long id;
    //班级名称
    private String name;
    //班级人数
    private Integer number;
    //班主任 id
    private Long employeeId;

    //员工对象
    private Employee employee;
    //写着写着,就习惯啦
    public String getJson(){
        //用一个 MAP 来封装数据,那么经过 JSON  解析后,就变成了 JSON 对象了
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id",id );
        map.put("name",name );
        map.put("number",number );
        map.put("employee",employee);

        //因为在装换的时候,  this  会调用自身的 get方法,拿到当前的值,那么现在就是一个是循环了
        //不能传 this,不然会死循环
        return JSON.toJSONString(map);
    }
}