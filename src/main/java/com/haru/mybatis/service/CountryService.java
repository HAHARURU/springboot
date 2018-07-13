package com.haru.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.haru.mybatis.enu.ErrorEnum;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.mapper.CityMapper;
import com.haru.mybatis.mapper.CountryMapper;
import com.haru.mybatis.model.City;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.model.vo.CityVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author HARU
 * @since 2018/5/28
 */
@Service
public class CountryService {
    static Logger logger = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CityMapper cityMapper;

    public List<City> getCity(CityVo cityVo) {
        List<City> cityWithCountry = cityMapper.getCityWithCountry(cityVo);

        return cityWithCountry;
    }

    public List<Country> getAllCountries() {
        return countryMapper.getAllCountries();
    }

    @Transactional
    public Country insertCountry(Country country) {
        List<Country> countryList = new ArrayList<>();
        countryList.add(country);
        if (StringUtils.isEmpty(country.getId())) {
            this.insertCountries(countryList);
        } else {
            //update country

        }
        if (countryList.size() > 0) {
            List<City> cities = country.getCities();
            cities.forEach(item -> {
                item.setCountry(countryList.get(0));
                item.setId(UUID.randomUUID().toString().trim());
                item.setCreateTime(new Timestamp(new Date().getTime()));
                if (cityMapper.insertCity(item) <= 0) {
                    throw new CustomException("城市" + ErrorEnum.保存失败.name(), String.valueOf(ErrorEnum.保存失败.getValue()));
                }
                item.setCountry(null);
            });
        }
        return countryList.get(0);
    }

    @Transactional
    public List<Country> insertCountries(List<Country> countryList) {
        countryList.forEach(item -> {
            item.setId(UUID.randomUUID().toString().trim());
            item.setCreateTime(new Timestamp(new Date().getTime()));
        });
        if (countryMapper.insertCountries(countryList) <= 0) {
            throw new CustomException("国家" + ErrorEnum.保存失败.name(), String.valueOf(ErrorEnum.保存失败.getValue()));
        }
        return countryList;
    }

    @Transactional
    public City insertCity(City city) {
        if (city.getCountry() == null) {
            throw new CustomException("国家" + ErrorEnum.不存在无法保存.name(), String.valueOf(ErrorEnum.不存在无法保存.getValue()));
        }
        List<Country> countryList = new ArrayList<>();
        if (StringUtils.isEmpty(city.getCountry().getId())) {
            countryList.add(city.getCountry());
            this.insertCountries(countryList);
        } else {
            //update country
        }
        city.setId(UUID.randomUUID().toString().trim());
        city.setCountry(countryList.get(0));
        city.setCreateTime(new Timestamp(new Date().getTime()));
        if (cityMapper.insertCity(city) <= 0) {
            throw new CustomException("城市" + ErrorEnum.保存失败.name(), String.valueOf(ErrorEnum.保存失败.getValue()));
        }
        return city;
    }


}
