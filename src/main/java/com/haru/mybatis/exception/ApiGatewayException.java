package com.haru.mybatis.exception;

/**
 * @author HARU
 * @since 2018/8/2
 */
public class ApiGatewayException extends Exception {

    private static final long serialVersionUID = 2610705772431867996L;

    protected String msg;

    public ApiGatewayException() {
        super();
    }

    public ApiGatewayException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ApiGatewayException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
