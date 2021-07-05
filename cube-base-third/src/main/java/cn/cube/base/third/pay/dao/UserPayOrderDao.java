package cn.cube.base.third.pay.dao;

import cn.cube.base.core.cache.BaseCache;
import cn.cube.base.core.db.DB;
import cn.cube.base.third.pay.entity.UserPayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description:UserPaymentOrderDao
 * Author:zhanglida
 * Date:2020/3/11
 * Email:406504302@qq.com
 */
@Repository
public class UserPayOrderDao extends BaseCache<Long, UserPayOrder> {
    @Autowired
    private DB db;

    public int insert(UserPayOrder order) {
        return db.insert(order);
    }

    public UserPayOrder select(Long id) {
        return db.from(UserPayOrder.class).where("id", id).first(UserPayOrder.class);
    }

    public int update(UserPayOrder order){
        return db.update(order,"status","error","tradeNo");
    }

    public int update(Long id ,Integer newStatus ,Integer oldStatus){
        return db.execute("update user_pay_order set `status` = ? where id = ? and `status` = ?",newStatus,id,oldStatus);
    }
}
