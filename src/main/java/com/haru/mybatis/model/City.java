package com.haru.mybatis.model;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author HARU
 * @since 2018/7/10
 */
public class City extends BaseEntity implements Serializable {
    /**
     * 城市名
     */
    private String cityName;
    /**
     * 描述
     */
    private String description;

    private Country country;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
