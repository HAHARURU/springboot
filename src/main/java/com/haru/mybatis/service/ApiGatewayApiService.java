package com.haru.mybatis.service;

import org.springframework.stereotype.Service;

/**
 * @author HARU
 * @since 2018/8/2
 */
@Service("ApiGatewayApiService")
public class ApiGatewayApiService extends ApiGatewayAbstractService {
    @Override
    public String getScanClassName() {
        return "com.haru.mybatis";
    }
}
