package com.tcg.mis.common.page;

/**
 * 分页控件VO对象 2014-8-22 上午09:22:55
 */
public class PaginationVO {
    private int page = 1;// 当前页，默认第一页
    private int total = -1;// 总记录条数
    private int size = 10;// 每页显示记录数，默认一页10条

    public PaginationVO() {
        super();
    }

    public PaginationVO(int curPage, int size) {
        this.page = curPage;
        this.size = size;
    }

    public int getSize() {
        return size;

    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPageNumbers() {
        if (total % size == 0) {
            return total / size;
        } else {
            return total / size + 1;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 得到记录起始位置
     *
     * @return
     */
    public int getStart() {

        if (page > this.getTotalPageNumbers()) {
            page = this.getTotalPageNumbers();
        }
        if (page < 1) {
            page = 1;
        }
        return (page - 1) * size;
    }

    /**
     * 得到记录结束位置
     *
     * @return
     */
    public int getEnd() {
        if (getStart() + size > total) {
            return total;
        } else {
            return getStart() + size;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
