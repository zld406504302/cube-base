package cn.cube.base.third.keruyun.response;

import lombok.Data;

import java.util.List;

/**
 * Description:Pager
 * Author:zhanglida
 * Date:2021/3/19
 * Email:406504302@qq.com
 */
@Data
public class Pager<T> {
    private Long totalRows;
    private Long pageSize;
    private List<T> items;
}
