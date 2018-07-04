package com.haru.mybatis.dto;

import java.io.Serializable;

/**
 * @author HARU
 * @since 2018/5/22
 *
 * 统一返回类
 */
public class ResultDto<T> implements Serializable {
    private String code;
    private String message;
    private T data;

    public ResultDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}