package cn.wolfcode.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


/**
 * 字典目录
 */
@Getter
@Setter
public class SystemDictionary {
    private Long id;
    //编码
    private String sn;
    //标题
    private String title;
    //描述
    private String intro;

    //写着写着,就习惯啦
    public String getJson(){
        //用一个 MAP 来封装数据,那么经过 JSON  解析后,就变成了 JSON 对象了
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id",id );
        map.put("title",title );
        map.put("sn",sn );
        map.put("intro",intro );

        //因为在装换的时候,  this  会调用自身的 get方法,拿到当前的值,那么现在就是一个是循环了
        //不能传 this,不然会死循环
        return JSON.toJSONString(map);
    }
}