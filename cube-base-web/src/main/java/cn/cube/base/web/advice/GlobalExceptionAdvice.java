package cn.cube.base.web.advice;

import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.web.protocol.response.ProtocolResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:全局异常处理Advice
 * 1：拦截添加@Rescontroller 或者 @ResponseBody注解接口相关异常
 * 2：将异常信息封装到统一结构体中
 * Author:zhanglida
 * Date:2017/7/3
 * Email:406504302@qq.com
 */
//@ControllerAdvice
//@RestController
public class GlobalExceptionAdvice {
    private static final Logger logger = LoggerUtils.getLogger(GlobalExceptionAdvice.class);
    public static final String PAGE_404 = "error/404";
    public static final String PAGE_500 = "error/500";
    //页面请求标记
    public static final String PAGE_TAG = ".html";

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Object requestHandlingNoHandlerFound(HttpServletRequest request, Exception exception) {
//        if (isPageAccess(request)) {
//            return new ModelAndView(PAGE_404);
//        }
//        ProtocolResponse resp = new ProtocolResponse(CommonBusinessError.REQUEST_URL_NOT_FUND);
//        logger.error(resp.getMsg(), exception);
//        return resp;
        return new ModelAndView(PAGE_404);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object missingParameterHandler(HttpServletRequest request, Exception exception) {
        if (isPageAccess(request)){
            return new ModelAndView(PAGE_404);
        }
        ProtocolResponse resp = new ProtocolResponse(BaseBusinessErrorCode.SYSTEM_UNKNOWN_ERROR);
        if(exception instanceof  MissingServletRequestParameterException){
            resp = new ProtocolResponse(BaseBusinessErrorCode.REQUEST_PARAMS_NULL);
        }
        logger.error(exception.getMessage(), exception);
        return resp;
    }

    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {
        if (isPageAccess(request)) {
            return new ModelAndView(PAGE_500);
        }
        ProtocolResponse resp = new ProtocolResponse();
        if (e instanceof BusinessException) {
            resp.setCode(((BusinessException) e).getCode());
            resp.setMsg(((BusinessException) e).getMsg());
            resp.setResult(((BusinessException) e).getResult());
        } else {
            resp.setCode(BaseBusinessErrorCode.SYSTEM_UNKNOWN_ERROR.getCode());
            resp.setMsg(BaseBusinessErrorCode.SYSTEM_UNKNOWN_ERROR.getMessage());
        }
        logger.error(resp.getMsg(), e);
        return resp;
    }

    public boolean isPageAccess(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        //return requestURI.endsWith(PAGE_TAG);
        return true;
    }

}
