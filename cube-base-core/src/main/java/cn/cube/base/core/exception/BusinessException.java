package cn.cube.base.core.exception;


import cn.cube.base.core.util.StringUtils;

/**
 * Created by Administrator on 2017/3/16.
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String msg;
    private Object result;

    public BusinessException(IBusinessError errorCode, String... args) {
        super(StringUtils.isFormatStr(errorCode.getMessage()) ? StringUtils.format(errorCode.getMessage(), args) : (null != args && args.length > 0) ? args[0]:errorCode.getMessage());
        this.code = errorCode.getCode();
        this.msg = getMessage();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BusinessException(Object result, IBusinessError protocolErrorCode, String... args) {
        this.code = protocolErrorCode.getCode();
        this.msg = StringUtils.format(protocolErrorCode.getMessage(), args);

        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getResult() {
        return result;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
