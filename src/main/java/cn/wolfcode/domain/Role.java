package cn.wolfcode.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/*
* 角色
* */
@Getter
@Setter
public class Role {
    private Long id;

    private String name;

    private String sn;

    //权限集合
    //list是集合
    private List<Permission> permissions=new ArrayList<>();
}