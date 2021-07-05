package cn.cube.base.demo.service;

import cn.cube.base.core.cache.CacheExpire;
import cn.cube.base.core.cache.KeyParam;
import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.*;
import cn.cube.base.web.interceptor.ILoginService;
import org.slf4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description:LoginTokenService
 * Author:zhanglida
 * Date:2020/3/16
 * Email:406504302@qq.com
 */
@Service
public class LoginTokenService implements ILoginService {
    private static final Logger logger = LoggerUtils.getLogger(LoginTokenService.class);
    @Autowired
    private UserService userService;

    @Override
    public void check(Long uid, String token) throws BusinessException {

    }

}
