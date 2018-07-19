package com.haru.mybatis.model;

import com.haru.mybatis.enu.StateEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author HARU
 * @since 2018/5/28
 */
@Entity
@Table(name = "country")
public class Country extends BaseEntity implements Serializable {
    /**
     * 名称
     */
    @Column(length = 20)
    private String name;

    /**
     * 代码
     */
    @Column(length = 20)
    private String code;

    /**
     * 状态
     */
//    @ColumnType(jdbcType = JdbcType.VARCHAR, typeHandler = BaseEnumTypeHandler.class)
    @Enumerated(EnumType.STRING)
    private StateEnum state;

    @Transient
    private List<City> cities;

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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
