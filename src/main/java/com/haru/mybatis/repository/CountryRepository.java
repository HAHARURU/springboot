package com.haru.mybatis.repository;

import com.haru.mybatis.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
