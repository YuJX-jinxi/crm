package cn.wolfcode.domain;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

/**
 * 字典明细
 */
@Setter
@Getter
@ToString
public class SystemDictionaryItem {
    private Long id;
    //关联的字典目录主键
    private Long parentId;
    //标题
    private String title;
    //序列号
    private Integer sequence;

    //写着写着,就习惯啦
    public String getJson(){
        //用一个 MAP 来封装数据,那么经过 JSON  解析后,就变成了 JSON 对象了
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id",id );
        map.put("parentId",parentId );
        map.put("title",title );
        map.put("sequence",sequence );

        //因为在装换的时候,  this  会调用自身的 get方法,拿到当前的值,那么现在就是一个是循环了
        //不能传 this,不然会死循环
        return JSON.toJSONString(map);
    }
}