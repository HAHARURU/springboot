package com.haru.mybatis.repository;

import com.haru.mybatis.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, String> {
}
