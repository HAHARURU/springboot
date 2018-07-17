package com.haru.mybatis.mapper;

import com.haru.mybatis.model.City;
import com.haru.mybatis.model.vo.CityVo;

import java.util.List;

/**
 * @author HARU
 * @since 2018/7/11
 */
public interface CityMapper {
    List<City> getCityWithCountry(CityVo cityVo);

    int insertCity(City city);

    int updateCities(List<City> cities);
}
