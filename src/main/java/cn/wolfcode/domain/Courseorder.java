package cn.wolfcode.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;


@Getter
@Setter
public class Courseorder {
    private Long id;
    //销售时间
    //跟进时间    跟进前端传进的数据修改编码格式
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;
    //潜在客户
    private Customer customer;
    //班级课程
    private ClassInfo classinfo;
    //金额
    private BigDecimal money;

    //写着写着,就习惯啦
    public String getJson(){
        //用一个 MAP 来封装数据,那么经过 JSON  解析后,就变成了 JSON 对象了
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id",id );
        map.put("customerId",customer.getId() );
        map.put("customerName",customer.getName());
        map.put("classinfoId",classinfo.getId() );
        map.put("money",money );

        //因为在装换的时候,  this  会调用自身的 get方法,拿到当前的值,那么现在就是一个是循环了
        //不能传 this,不然会死循环
        return JSON.toJSONString(map);
    }
}