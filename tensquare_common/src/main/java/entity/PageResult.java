package entity;
/*
 * 分页结果类
 *  @param <T>
 * */

import java.util.List;

public class PageResult<T> {

    private long total;
    private List<T> rows;

    //无参
    public PageResult() {
    }

    // 有参构造器
    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    //set get方法
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
