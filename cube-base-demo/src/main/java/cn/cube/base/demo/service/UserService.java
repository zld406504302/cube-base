package cn.cube.base.demo.service;

import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:UserService
 * Author:zhanglida
 * Date:2018/4/8
 * Email:406504302@qq.com
 */
@Service
public class UserService {
    public void check(Long uid, String token) throws BusinessException {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_NULL);
        }
    }

}
