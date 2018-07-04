package com.haru.mybatis.model;

import com.haru.mybatis.enu.StateEnum;
import com.haru.mybatis.util.BaseEnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import java.io.Serializable;

/**
 * @author HARU
 * @since 2018/5/28
 */
public class Country extends BaseEntity implements Serializable {
    /**
     * 名称
     */
    private String name;

    /**
     * 代码
     */
    private String code;

    /**
     * 状态
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = BaseEnumTypeHandler.class)
    private StateEnum state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }
}
