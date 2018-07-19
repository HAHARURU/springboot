package com.haru.mybatis.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author HARU
 * @since 2018/7/10
 */
@Entity
@Table(name = "city")
public class City extends BaseEntity implements Serializable {
    /**
     * 城市名
     */
    @Column(name = "city_name", length = 25)
    private String cityName;
    /**
     * 描述
     */
    @Column(length = 25)
    private String description;

    @Transient
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
