package com.haru.mybatis.exception;

/**
 * @author HARU
 * @since 2018/8/2
 */
public class ClassNotFoundApiGatewayException extends ApiGatewayException {

    public ClassNotFoundApiGatewayException() {
        super();
    }

    public ClassNotFoundApiGatewayException(String msg) {
        super(msg);
    }

    public ClassNotFoundApiGatewayException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
