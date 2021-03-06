package com.ismyself.entity;

import java.io.Serializable;
import java.util.List;

/**
 * package com.ismyself.entity;
 *
 * @auther txw
 * @create 2019-07-26  19:48
 * @description：
 */
public class PageResult implements Serializable {
    private Long total;//总记录数
    private List rows;//当前页结果

    public PageResult() {
    }

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public List getRows() {
        return rows;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
