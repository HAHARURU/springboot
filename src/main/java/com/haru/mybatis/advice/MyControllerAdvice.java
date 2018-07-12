package com.haru.mybatis.advice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haru.mybatis.dto.ResultDto;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.util.GsonView;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author HARU
 * @since 2018/5/23
 *
 * 统一异常处理
 */
@ControllerAdvice
public class MyControllerAdvice {
    private Gson gson;

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder();
            GsonView.regGson(gb);
            gson = gb.create();
        }
        return gson;
    }
    /**
     * 拦截捕捉自定义异常
     */
    @ResponseBody
    @ExceptionHandler(value = CustomException.class)
    public String myErrorHandler(CustomException ex) {
        return getGson().toJson(new ResultDto<Object>(ex.getCode(), ex.getMessage(), null));
    }
}