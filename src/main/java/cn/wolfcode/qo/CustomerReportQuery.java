package cn.wolfcode.qo;

import cn.wolfcode.shiro.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
@Getter
@Setter
public class CustomerReportQuery extends QueryObject{
    //分组的类型
    private String groupType="e.name";
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public Date getEndDate() { // 获取结束时间当天最晚的时候
        return DateUtil.getEndDate(endDate);
    }
}
