package com.haru.mybatis.model.vo;

import com.haru.mybatis.model.City;

import java.util.List;

/**
 * @author HARU
 * @since 2018/7/11
 */
public class CityVo {
    private City city;

    private List<String> names;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
