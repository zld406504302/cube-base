package cn.cube.base.core.exception;



public class DataCheckException extends Exception {

    public DataCheckException(String message) {
        super(message);
    }

    public DataCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
