package cn.wolfcode.qo;

import cn.wolfcode.util.UserContext;
import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class QueryObject {

    //关键字查询
    private String keyword;

    private Long classId;
    //当前页
    private int currentPage = 1;

    //当前页要显示的数据
    private int pageSize = 3;

    //查询数据的开始索引位置
    /*private int getStart(){
        return (currentPage-1)*pageSize;
    }*/

    public String getKeyword(){
        //给关键字判空,和判断是否空字符串
        if (StringUtils.hasText(keyword)){
            //trim : 去除当前字符串内的空字符串
            //replaceAll : 把全部空字符串转成空
            return keyword.replaceAll(" ","");
        }
        return null;
    }
}
