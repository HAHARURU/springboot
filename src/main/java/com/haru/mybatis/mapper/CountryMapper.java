package com.haru.mybatis.mapper;

import com.haru.mybatis.model.Country;

import java.util.List;

/**
 * @author HARU
 * @since 2018/5/28
 */
public interface CountryMapper {
    List<Country> getAllCountries();

    int insertCountries(List<Country> countryList);

    int updateCountry(Country country);
}
