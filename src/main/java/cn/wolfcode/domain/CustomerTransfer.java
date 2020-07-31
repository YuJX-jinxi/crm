package cn.wolfcode.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CustomerTransfer {
    private Long id;
    //客户
    private Customer customer;
    //操作人
    private Employee operator;
    //操作时间
    private Date operateTime;
    //旧销售人员
    private Employee oldSeller;
    //新销售人员
    private Employee newSeller;
    //移交原因
    private String reason;
}