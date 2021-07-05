package cn.cube.base.core.exception;

/**
 * 错误码定义类
 * <p>
 * 1000 - 9999 为系统错误码
 */
public enum BaseBusinessErrorCode implements IBusinessError {
    NO_ERROR(0, "成功"),
    SYSTEM_UNKNOWN_ERROR(900, "系统未知错误"),
    SYSTEM_IS_BUSY(901, "系统繁忙,稍后再试"),
    REQUEST_PARAMS_INVALID(1001, "请求参数非法"),
    REQUEST_API_IS_NULL(1002, "请求接口为空"),
    REQUEST_PARAMS_NULL(1003, "请求参数为空"),
    REQUEST_SIGN_INVALID(1004, "请求签名无效"),
    REQUEST_TOKEN_INVALID(1005, "请求token无效"),
    REQUEST_AUTH_EXPIRED(1006, "请求token已过期"),
    REQUEST_APP_NOT_EXISTS(1007, "请求应用不存在"),
    REQUEST_USER_NOT_EXISTS(1008, "请求用户不存在"),
    REQUEST_IS_FREQUENTLY(1009, "操作频繁，稍后再试"),
    ;

    BaseBusinessErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
