package cn.cube.base.web.protocol.response;


import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.exception.IBusinessError;
import cn.cube.base.core.util.StringUtils;

/**
 * Description:ProtocolResponse
 * Author:zhanglida
 * Date:2017/12/24
 * Email:406504302@qq.com
 */
public class ProtocolResponse<T> {
    private int code;
    private String msg;
    private T result;
    private String timestamp;

    public ProtocolResponse() {
    }

    public ProtocolResponse(BusinessException exception) {
        this.code = exception.getCode();
        this.msg = exception.getMessage();
    }

    public ProtocolResponse(IBusinessError errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMessage();
    }
    public void setProtocolError(IBusinessError protocolErrorCode, String... params) {
        this.code = protocolErrorCode.getCode();
        this.msg = StringUtils.format(protocolErrorCode.getMessage(), params);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
