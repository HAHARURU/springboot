package com.haru.mybatis.exception;

/**
 * @author HARU
 * @since 2018/8/2
 */
public class AccessApiGatewayException extends ApiGatewayException {

    private static final long serialVersionUID = -3819826501917757412L;

    public AccessApiGatewayException() {
        super();
    }

    public AccessApiGatewayException(String msg) {
        super(msg);
    }

    public AccessApiGatewayException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
