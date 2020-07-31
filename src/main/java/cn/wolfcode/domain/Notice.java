package cn.wolfcode.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@ToString
public class Notice {
    private Long id;
    //发布人 员工 id
    private Employee employee;
    //发布时间   存入的时候设置时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pubdate;
    //标题
    private String title;
    //内容
    private String content;
    //已读状态
    private Integer status=0;
}