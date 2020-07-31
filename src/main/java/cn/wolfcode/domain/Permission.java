package cn.wolfcode.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限
 */
@Setter
@Getter
public class Permission {
    private Long id;
    //名称 (给分配权限的人看的)
    private String name;
    //表达式 (给程序判断的时候用的)
    private String expression;


}