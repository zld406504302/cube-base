package cn.cube.base.web.handler;

import cn.cube.base.core.entity.IAppInfo;
import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.service.IAppInfoService;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.core.util.StringUtils;
import cn.cube.base.web.ServerContext;
import cn.cube.base.web.filter.WebProtocolFilter;
import cn.cube.base.web.interceptor.ILoginService;
import cn.cube.base.web.protocol.request.ProtocolRequest;
import cn.cube.base.web.protocol.response.ProtocolResponse;
import org.slf4j.Logger;

/**
 * Description:EncryptHttpHandler
 * Author:zhanglida
 * Date:2020/11/5
 * Email:406504302@qq.com
 */
public class VertxEncryptHttpHandler extends VertxCommonHttpHandler {
    private  final Logger logger = LoggerUtils.getLogger(VertxEncryptHttpHandler.class);
    private ILoginService loginService;
    private IAppInfoService appInfoService;

    public VertxEncryptHttpHandler(String method, HandlerProxy apiInvocation, ILoginService loginService, IAppInfoService appInfoService) {
        super(method,apiInvocation);
        this.loginService = loginService;
        this.appInfoService = appInfoService;

    }

    /**
     * api 接口处理入口
     *
     * @param request
     * @return
     */
    @Override
    public ProtocolResponse handleApiMethod(ProtocolRequest request) {
        ProtocolResponse response = new ProtocolResponse();

        // 获取用户ID
        Long uid = request.getUid();

        String appId = request.getAppId();
        if (StringUtils.isEmpty(appId)){
            // method 为空
            response.setProtocolError(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID);
            return response;
        }
        // 获取 method
        if (StringUtils.isEmpty(getMethod())) {
            // method 为空
            response.setProtocolError(BaseBusinessErrorCode.REQUEST_API_IS_NULL);
            return response;
        }
        HandlerProxy apiInvocation = getApiInvocation();
        // 查找method函数
        if (apiInvocation == null) {
            response.setProtocolError(BaseBusinessErrorCode.REQUEST_API_IS_NULL);
            return response;
        }

        // 校验登录状态
        if (apiInvocation.auth()) {
            String token = request.getToken();
            try {
                loginService.check(uid, token);
            } catch (BusinessException e) {
                response.setCode(e.getCode());
                response.setMsg(e.getMsg());
                return response;
            }
        }


        //解析业务参数
        String decryptBizData = "";

        try {
            // 获取应用信息
            IAppInfo appInfo = appInfoService.getAppInfo(appId);
            if (null == appInfo) {
                response.setProtocolError(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID);
                return response;
            }

            String aesKey = appInfo.getAesKey();
            //校验签名
            WebProtocolFilter.checkSign(request, appInfo.getSignKey());
            //设置商户类型
            request.setMerchant(appInfo.getMerchant());
            //解析业务参数
            decryptBizData = WebProtocolFilter.decryptDataToStr(request, aesKey);
            request.setData(decryptBizData);
            // 设置线程变量 - 协议请求
            ServerContext.setProtocolRequest(request);

            Object result = apiInvocation.invoke(decryptBizData);
            response.setResult(result);
            response.setProtocolError(BaseBusinessErrorCode.NO_ERROR);

        } catch (Throwable t) {
            handleError(request,response,t);
        } finally {
            /** 删除线程变量 */
            ServerContext.removeProtocolRequest();
        }

        return response;
    }

    @Override
    public  Logger getLogger() {
        return logger;
    }


}
