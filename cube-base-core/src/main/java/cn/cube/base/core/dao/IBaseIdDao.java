package cn.cube.base.core.dao;

import java.util.List;

/**
 * Description:IIDLongDao
 * Author:zhanglida
 * Date:2018/3/11
 * Email:406504302@qq.com
 */
public interface IBaseIdDao<K,IdEntity> extends IBaseDao<IdEntity>{
    IdEntity select(K id);
    List<IdEntity> selectList(List<K> ids);
}
