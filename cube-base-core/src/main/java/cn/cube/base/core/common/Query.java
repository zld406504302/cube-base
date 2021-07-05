package cn.cube.base.core.common;


import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Description:Query
 * Author:zhanglida
 * Date:2018/2/23
 * Email:406504302@qq.com
 */
@Data
public class Query<T> extends Paging {

    private List<Object> params = Lists.newArrayList();
    /**
     * 操作类型
     *
     * @see Operate
     */
    private Operate operate;
    /**
     * 排序名对应的值
     */
    // TODO 确认该处的值是否都是Number.  如果是，使用Number
    private T sortValue;
    public void addParam(Object param) {
        this.params.add(params);
    }

    public void setParams(List<Object> params) {
        this.params.clear();
        this.params.addAll(params);
    }

    public List<Object> getParams() {
        return this.params;
    }


    /**
     * 操作方向
     * UP:向上滑
     * DOWN:向下滑
     */
    public enum Operate {
        UP, DOWN
    }

}
