package com.haru.mybatis.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.util.GsonView;
import com.haru.mybatis.util.encryption.MD5;
import com.haru.mybatis.util.encryption.SortCollection;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author HARU
 * @since 2018/7/30
 */
@Aspect
@Component
public class InterceptorAspect implements Ordered {
    private static final Logger logger = LoggerFactory.getLogger(InterceptorAspect.class);

    private Gson gson;

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder();
            GsonView.regGson(gb);
            gson = gb.create();
        }
        return gson;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Pointcut("target(com.haru.mybatis.service.imp.CountryServiceImp) && @annotation(com.haru.mybatis.annotation.SignAnnotation)")
    private void validateSign() {
    }

    @Around("validateSign()")
    public Object restfulAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args.length == 2) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) args[0];
            String countryJSON = (String) args[1];

            Map<String, String> params = getGson().fromJson(countryJSON, new TypeToken<Map<String, Object>>() {
            }.getType());

            ConcurrentSkipListMap cbody = new ConcurrentSkipListMap();
            cbody.putAll(params);
            String sourceStr = SortCollection.sort(null, cbody);    //排序
            String sign = MD5.sign(sourceStr, "haruKey", "UTF-8");      //加密

            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            if (parameterMap != null && parameterMap.size() > 0 && parameterMap.get("sign").length > 0 &&
                    StringUtils.isNotEmpty(parameterMap.get("sign")[0]) && parameterMap.get("sign")[0].equals(sign)) {
            } else {
                throw new CustomException("签名错误");
            }
        }
        return joinPoint.proceed();
    }
}
