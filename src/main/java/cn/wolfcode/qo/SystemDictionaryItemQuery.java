package cn.wolfcode.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemDictionaryItemQuery extends QueryObject{
    //给 qo 对象重新封装了一下,给他添加了一个查询条件,用于明细查询
    private Long parentId;//目录id
}
