package com.haru.mybatis.interceptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haru.mybatis.enumPackage.ErrorEnum;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.util.GsonView;
import com.haru.mybatis.util.encryption.MD5;
import com.haru.mybatis.util.encryption.SortCollection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author HARU
 * @since 2018/7/30
 */
public class MyInterceptor implements HandlerInterceptor {
    static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

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
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
//
//        ServletInputStream inputStream = httpServletRequest.getInputStream();
//        int contentLength = httpServletRequest.getContentLength();
//        byte[] bytes = new byte[contentLength];
//        inputStream.read(bytes, 0, contentLength);
//        String json = new String(bytes, "UTF-8");
//        logger.info(json);
//        Map<String, String> params = getGson().fromJson(json, new TypeToken<Map<String, Object>>() {
//        }.getType());
//
//        ConcurrentSkipListMap cbody = new ConcurrentSkipListMap();
//        cbody.putAll(params);
//        String sourceStr = SortCollection.sort(null, cbody);    //排序
//        String sign = MD5.sign(sourceStr, "haruKey", "UTF-8");      //加密
//        if (parameterMap != null && parameterMap.size() > 0 && parameterMap.get("sign").length > 0 &&
//                StringUtils.isNotEmpty(parameterMap.get("sign")[0]) && parameterMap.get("sign")[0].equals(sign)) {
            return true;
//        } else {
//            throw new CustomException(ErrorEnum.更新失败.toString(), String.valueOf(ErrorEnum.更新失败.getValue()));
//        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
