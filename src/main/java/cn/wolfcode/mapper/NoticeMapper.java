package cn.wolfcode.mapper;

import cn.wolfcode.domain.Notice;
import cn.wolfcode.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Notice record);

    Notice selectByPrimaryKey(Long id);

    List<Notice> selectAll();

    int updateByPrimaryKey(Notice record);

    List<Notice> selectForList(QueryObject qo);

    Notice selectSee(@Param("noticeId") Long noticeId, @Param("employeeId") Long employeeId);
}