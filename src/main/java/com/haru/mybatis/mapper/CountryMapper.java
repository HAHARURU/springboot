package com.haru.mybatis.mapper;

import com.haru.mybatis.model.Country;
import com.haru.mybatis.util.MyMapper;

import java.util.List;

/**
 * @author HARU
 * @since 2018/5/28
 */
public interface CountryMapper extends MyMapper<Country> {
    List<Country> getAllCountries();

    int insertCountries(List<Country> countryList);
}
