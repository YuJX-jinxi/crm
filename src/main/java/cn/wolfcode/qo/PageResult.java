package cn.wolfcode.qo;

import lombok.Getter;

import java.util.List;


@Getter
public class PageResult<T> {
    //qo带进来的四条数据
    private int currentPage;    //当前页

    private int pageSize;        //当前页显示多少条数据

    private int totalCount;     //总共有多少条数据

    private List<T>data;          //当前页需要显示的数据



    //通过计算出来的数据
    private int totalPage;          //总页数/末页

    private int prevPage;           //上一页

    private int nextPage;           //下一页


    public PageResult(int currentPage, int pageSize, int totalCount, List<T> data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;

        //计算 总页,上一页 ,下一页
        //总页
        this.totalPage=this.totalCount%this.pageSize==0?this.totalCount/this.pageSize : this.totalCount/this.pageSize+1;

        //上一页
        this.prevPage=this.currentPage-1>=1?this.currentPage-1: 1;

        //下一页
        this.nextPage=this.currentPage+1<=this.totalPage? this.currentPage+1:this.totalPage;
    }
}
