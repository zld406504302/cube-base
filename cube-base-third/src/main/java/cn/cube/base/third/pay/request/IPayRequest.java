package cn.cube.base.third.pay.request;

import cn.cube.base.core.exception.BusinessException;

/**
 * Description:IPayRequest
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public interface IPayRequest {
    //校验参数
    void check() throws BusinessException;
}
