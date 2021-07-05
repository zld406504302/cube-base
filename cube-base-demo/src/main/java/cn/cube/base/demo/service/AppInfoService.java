package cn.cube.base.demo.service;

import cn.cube.base.core.entity.IAppInfo;
import cn.cube.base.core.service.IAppInfoService;
import cn.cube.base.demo.dao.AppInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:AppInfoService
 * Author:zhanglida
 * Date:2020/10/12
 * Email:406504302@qq.com
 */
@Service
public class AppInfoService implements IAppInfoService {

    @Autowired
    private AppInfoDao appInfoDao;

    @Override
    public IAppInfo getAppInfo(String appId) {
        return appInfoDao.select(appId);
    }
}
