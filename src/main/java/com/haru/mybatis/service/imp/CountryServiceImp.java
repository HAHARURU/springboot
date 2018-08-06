package com.haru.mybatis.service.imp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haru.mybatis.annotation.SignAnnotation;
import com.haru.mybatis.enumPackage.ErrorEnum;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.mapper.CityMapper;
import com.haru.mybatis.mapper.CountryMapper;
import com.haru.mybatis.model.City;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.model.vo.CityVo;
import com.haru.mybatis.repository.CountryRepository;
import com.haru.mybatis.service.CountryService;
import com.haru.mybatis.util.GsonView;
import com.haru.mybatis.util.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author HARU
 * @since 2018/5/28
 */
@Service("CountryService")
public class CountryServiceImp implements CountryService {
    static Logger logger = LoggerFactory.getLogger(CountryServiceImp.class);

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RedisCache redisCache;

    private Gson gson;

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder();
            GsonView.regGson(gb);
            gson = gb.create();
        }
        return gson;
    }

    @SignAnnotation
    public void validateSign(HttpServletRequest request, String countryJSON) {
    }

    @Override
    public String test(String name, Integer age) {
//        String s = null;
//        s.toString();
        return name + age;
    }

    @Transactional
    public Country insertCountryOnly(Country country) {
        List<Country> all = countryRepository.findAll();
        return countryRepository.save(country);
    }

    public List<City> getCity(CityVo cityVo) {
        List<City> cityWithCountry = cityMapper.getCityWithCountry(cityVo);
        return cityWithCountry;
    }

    public List<Country> getAllCountries() {
        List<Country> allCountries = countryMapper.getAllCountries();
        redisCache.set("allCountries", this.getGson().toJson(allCountries));
        return allCountries;
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
        if (countryList.size() > 0 && country.getCities() != null) {
            List<City> cities = country.getCities();
            cities.forEach(item -> {
                item.setCountry(countryList.get(0));
                item.setId(UUID.randomUUID().toString().trim());
                item.setCreateTime(new Timestamp(new Date().getTime()));
                if (cityMapper.insertCity(item) <= 0) {
                    throw new CustomException("城市" + ErrorEnum.保存失败.name(), String.valueOf(ErrorEnum.保存失败.getValue()));
                }
                item.setCountry(null);  //避免关联循环
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

    @Transactional
    public Country updateCountryWithCity(Country country) {
        country.setVersion(country.getVersion() + 1);
        if (countryMapper.updateCountry(country) <= 0) {
            throw new CustomException("国家" + ErrorEnum.更新失败.name(), String.valueOf(ErrorEnum.更新失败.getValue()));
        }
        if (cityMapper.updateCities(country.getCities()) <= 0) {
            throw new CustomException("城市" + ErrorEnum.更新失败.name(), String.valueOf(ErrorEnum.更新失败.getValue()));
        }
        return country;
    }

    public void redisCountry() {
        Country country = countryRepository.findByCode("CN");
        System.out.println("第一次查询：" + country.getName());

        Country country2 = countryRepository.findByCode("CN");
        System.out.println("第二次查询：" + country2.getName());
    }
}