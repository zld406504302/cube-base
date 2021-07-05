package cn.cube.base.core.dao;

import cn.cube.base.core.db.DB;
import cn.cube.base.core.util.Beans;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Description:BaseDao
 * Author:zhanglida
 * Date:2020/8/26
 * Email:406504302@qq.com
 */
public class BaseDao<T> implements IBaseDao<T> {
    @Autowired
    private DB db;

    public <T, K> T select(K id) {
        Class<T> vclass = Beans.getGenericType(getClass());
        return db.from(vclass).where("id", id).first(vclass);
    }

    @Override
    public int insert(T t) {
        return db.insert(t);
    }

    @Override
    public int delete(T t) {
        return db.delete(t);
    }

    @Override
    public int update(T t) {
        return db.update(t);
    }

    @Override
    public int update(T t, String... properties) {
        return db.update(t, properties);
    }

    @Override
    public DB getDB() {
        return this.db;
    }

    public int batchInsert(List<T> tList) {
        return db.batchInsert(tList);
    }

    public int batchIgnoreInsert(List<T> tList) {
        return db.batchIgnoreInsert(tList);
    }

    public int[] batchUpdate(String sql, List<Object[]> parmas) {
        return db.batchUpdate(sql, parmas);
    }

    @Override
    public List<T> selectList(Map<String, Object> params) {
        Class<T> vclass = Beans.getGenericType(getClass(), 0);
        return db.from(vclass).where(params).all(vclass);
    }

    public List<T> selectList(List<Long> idList) {
        Class<T> vclass = Beans.getGenericType(getClass(), 0);
        return db.from(vclass).in("id", idList).all(vclass);
    }

    @Override
    public List<T> selectList(List<Long> valueList,String paramName) {
        Class<T> vclass = Beans.getGenericType(getClass(), 0);
        return db.from(vclass).in(paramName, valueList).all(vclass);
    }
}
