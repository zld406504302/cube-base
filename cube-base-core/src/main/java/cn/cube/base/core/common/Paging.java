package cn.cube.base.core.common;

import java.io.Serializable;

/**
 * Description:分页对象
 * Author:zhanglida
 * Date:2017/3/24
 * Email:406504302@qq.com
 */
public class Paging implements Serializable {
    private final static int MAX_PAGE_SIZE = 100;
    //页码
    private int pageIndex = 1;
    //每页条数
    private int pageSize = 30;

    public Paging() {
    }

    public Paging(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex > 0 ? pageIndex : this.pageIndex;
        this.pageSize = pageSize > 0 ? pageSize : this.pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (pageIndex - 1) * pageSize;
    }

}
