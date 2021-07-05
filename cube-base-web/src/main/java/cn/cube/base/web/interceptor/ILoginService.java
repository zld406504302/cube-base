package cn.cube.base.web.interceptor;

import cn.cube.base.core.exception.BusinessException;

/**
 * Description:ILoginService
 * Author:zhanglida
 * Date:2020/3/15
 * Email:406504302@qq.com
 */
public interface ILoginService {
    void check(Long uid, String token) throws BusinessException;
}
