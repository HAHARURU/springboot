package com.haru.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.haru.mybatis.mapper.CityMapper;
import com.haru.mybatis.mapper.CountryMapper;
import com.haru.mybatis.model.City;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.model.vo.CityVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
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

    public Country getById(String id) {
        return countryMapper.selectByPrimaryKey(id);
    }

    public void deleteById(String id) {
        countryMapper.deleteByPrimaryKey(id);
    }

    public void save(Country country) {
        if (country.getId() != null) {
            countryMapper.updateByPrimaryKeySelective(country);
        } else {
            country.setId(UUID.randomUUID().toString().trim());
            country.setCreateTime(new Timestamp(new Date().getTime()));
            countryMapper.insert(country);
        }
    }

    public List<Country> getAll(int page, int size, String name) {
        PageHelper.startPage(page, size);
        Condition condition = new Condition(Country.class);
        Condition.Criteria criteria = condition.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        return countryMapper.selectByExample(condition);
    }

    public List<City> getCity(CityVo cityVo) {
        List<City> cityWithCountry = cityMapper.getCityWithCountry(cityVo);

        return cityWithCountry;
    }

    public List<Country> getAllCountries() {
        return countryMapper.getAllCountries();
    }
}
