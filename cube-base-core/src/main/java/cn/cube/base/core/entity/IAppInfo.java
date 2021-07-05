package cn.cube.base.core.entity;

import java.io.Serializable;

/**
 * Description:IAppInfo
 * Author:zhanglida
 * Date:2020/10/12
 * Email:406504302@qq.com
 */
public interface IAppInfo extends IEntity {
    String getAppId();

    String getAesKey();

    String getSignKey();

    Integer getMerchant();
}
