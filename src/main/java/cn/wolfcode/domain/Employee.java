package cn.wolfcode.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;

    private String name;

    private String password;

    private String email;

    private Integer age;

    private Long deptId;
    private boolean status=true;

    //不使用包装类型,因为包装类型的 Boolean 有三种状态
    private boolean admin; //权限

    //关联属性
    private Department dept;
/*
    public Long getDeptId(){
        return dept.getId();
    }*/

    //角色集合
    //list是集合
    private List<Role>roles=new ArrayList<>();

}