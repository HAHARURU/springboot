package com.haru.mybatis.mongoRepository;

import com.haru.mybatis.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryMongoRepository extends MongoRepository<Country, String> {
}
