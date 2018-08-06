package com.haru.mybatis.exception;

/**
 * @author HARU
 * @since 2018/8/2
 */
public class MethodNotFoundApiGatewayException extends ApiGatewayException {

    public MethodNotFoundApiGatewayException() {
        super();
    }

    public MethodNotFoundApiGatewayException(String msg) {
        super(msg);
    }

    public MethodNotFoundApiGatewayException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
