package cn.wolfcode.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerQuery extends QueryObject{
    //状态
    private Integer status;

    //销售员的id
    private Long sellerId;
}
