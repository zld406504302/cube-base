package cn.cube.base.core.dao;

import cn.cube.base.core.db.DB;

import java.util.List;
import java.util.Map;

/**
 * Description:IBaseDao
 * Author:zhanglida
 * Date:2020/8/26
 * Email:406504302@qq.com
 */
public interface IBaseDao<T> {
    int insert(T t);
    int delete(T t);
    int update(T t);
    public int update(T t, String... properties);
    DB getDB();
    List<T> selectList(Map<String,Object> params);
    List<T> selectList(List<Long> valueList,String paramName);
}
