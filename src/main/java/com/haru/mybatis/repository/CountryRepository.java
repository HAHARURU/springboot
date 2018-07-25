package com.haru.mybatis.repository;

import com.haru.mybatis.model.Country;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

//@CacheConfig(cacheNames = "country")
public interface CountryRepository extends JpaRepository<Country, String> {
//    @Cacheable(key = "#p0")
    Country findByCode(String code);

//    @CachePut(key = "#p0.code")
    Country save(Country country);
}
